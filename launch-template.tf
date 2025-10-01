resource "aws_launch_template" "webapp_lt" {
  name_prefix            = "webapp-lt"
  image_id               = var.webapp_ami_id
  instance_type          = var.instance_type
  key_name               = aws_key_pair.deployer.key_name
  vpc_security_group_ids = [aws_security_group.webapp_sg.id]

  user_data = base64encode(<<-EOF
    #!/bin/bash
    set -e

    MYSQL_IP="${aws_instance.mysql.private_ip}"

    echo "Updating environment variables..."
    sudo sed -i '/SPRING_DATASOURCE_URL/d' /etc/environment
    echo "export SPRING_DATASOURCE_URL=jdbc:mysql://$MYSQL_IP:3306/recommend?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true" | sudo tee -a /etc/environment
    sudo chmod 644 /etc/environment
    source /etc/environment

    echo "SPRING_DATASOURCE_URL is now set to: $SPRING_DATASOURCE_URL"

    sudo chown ubuntu:ubuntu /home/ubuntu/app/webapp.jar
    chmod +x /home/ubuntu/app/webapp.jar
    sudo touch /home/ubuntu/app/webapp.log
    sudo chown ubuntu:ubuntu /home/ubuntu/app/webapp.log
    sudo chmod 644 /home/ubuntu/app/webapp.log
    sudo chown -R ubuntu:ubuntu /home/ubuntu/app/
    sudo chmod -R 755 /home/ubuntu/app/

    sudo pkill -f "webapp.jar" || true
    echo "sleep before $(date)"
    echo "$(whoami)"
    sudo -u ubuntu nohup java -jar /home/ubuntu/app/webapp.jar > /home/ubuntu/app/webapp.log 2>&1 &
    echo "sleep after WebApp started successfully at $(date)"
  EOF
  )

  metadata_options {
    http_tokens   = "optional" # 启用 IMDSv1
    http_endpoint = "enabled"  # 启用元数据服务端点
  }
}