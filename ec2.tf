resource "aws_instance" "mysql" {
  ami               = var.mysql_ami_id
  availability_zone = "us-east-2c"
  instance_type     = var.instance_type
  subnet_id         = aws_subnet.private_subnet.id
  key_name          = aws_key_pair.deployer.key_name
  security_groups   = [aws_security_group.mysql_sg.id]
  #   iam_instance_profile = aws_iam_instance_profile.csye6225_instance_profile.name

  user_data = <<-EOF
      #!/bin/bash

      echo "Updating MySQL user permissions..."

      mysql -u root -p'${var.database_password}' -e "
        DROP USER IF EXISTS '${var.database_username}'@'10.0.0.7';
        CREATE USER '${var.database_username}'@'%' IDENTIFIED BY '${var.database_password}';
        GRANT ALL PRIVILEGES ON recommend.* TO '${var.database_username}'@'%';
        FLUSH PRIVILEGES;
      "
   EOF

  tags = {
    Name = "MySQL"
  }
}
