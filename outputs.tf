output "webapp_lb_dns" {
  description = "The DNS name of the load balancer for the web application"
  value       = aws_lb.api_nlb.dns_name
}

# output "api_url" {
#   value = "http://api.${var.domain_name}"
# }

output "mysql_private_ip" {
  description = "The private IP address of the MySQL database instance"
  value       = aws_instance.mysql.private_ip
}
