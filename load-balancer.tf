resource "aws_lb" "api_nlb" {
  name               = "api-nlb"
  internal           = false
  load_balancer_type = "network"
  ip_address_type    = "ipv4"
  subnets            = [aws_subnet.public_subnet.id]
  security_groups    = [aws_security_group.nlb_sg.id]

  enable_cross_zone_load_balancing = false
}

resource "aws_lb_target_group" "nlb_tg01" {
  name        = "nlb-tg01"
  port        = 8080
  protocol    = "TCP"
  vpc_id      = aws_vpc.main.id
  target_type = "instance"

  health_check {
    path                = "/v1/healthcheck"
    protocol            = "HTTP"
    port                = "8080"
    interval            = 30
    timeout             = 4
    healthy_threshold   = 2
    unhealthy_threshold = 2
    matcher             = "200-399"
  }
}

resource "aws_lb_listener" "api_listener" {
  load_balancer_arn = aws_lb.api_nlb.arn
  port              = 80
  protocol          = "TCP"

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.nlb_tg01.arn
  }
}

