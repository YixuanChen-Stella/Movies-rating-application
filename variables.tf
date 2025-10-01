variable "aws_region" {
  description = "AWS Region"
  default     = "us-east-2"
}

variable "webapp_ami_id" {
  description = "AMI ID for WebApp"
  type        = string
  default     = "ami-037555c0e18b72abc"
}

variable "mysql_ami_id" {
  description = "AMI ID for MySQL"
  type        = string
  default     = "ami-0291ac13f72d17c4a"
}

variable "database_username" {
  description = "Database Username"
  type        = string
  default     = "assignment3-database"
}

variable "database_password" {
  description = "Database Password"
  type        = string
  sensitive   = true
  default     = "Csye6225."
}

variable "instance_type" {
  description = "EC2 instance type"
  type        = string
  default     = "t2.micro"
}

variable "route53_zone_id" {
  description = "Route 53 Hosted Zone ID"
  type        = string
  default     = "Z0184808106N44H68NNZS"
}

variable "domain_name" {
  description = "Domain name for the application"
  type        = string
  default     = "api.yixuan016.me"
}

