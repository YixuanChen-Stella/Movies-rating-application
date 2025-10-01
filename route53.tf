resource "aws_route53_record" "api_cname" {
  zone_id = var.route53_zone_id
  name    = var.domain_name
  type    = "CNAME"
  ttl     = 60
  records = [aws_lb.api_nlb.dns_name]
}
