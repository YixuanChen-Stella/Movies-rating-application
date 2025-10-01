packer {
  required_plugins {
    amazon = {
      version = ">= 1.0.0"
      source  = "github.com/hashicorp/amazon"
    }
  }
}

# Define the source AMI (Ubuntu 24.04)
source "amazon-ebs" "ubuntu" {
  ami_name      = "my-database-ami-{{timestamp}}"
  instance_type = "t2.micro"
  region        = "us-east-2"
  source_ami    = "ami-0291ac13f72d17c4a"
  ssh_username  = "ubuntu"

  launch_block_device_mappings {
    device_name = "/dev/sda1"
    volume_size = 8
    volume_type = "gp3"
    delete_on_termination = true
  }

  launch_block_device_mappings {
    device_name = "/dev/sdx"
    volume_size = 8
    volume_type = "gp3"
    delete_on_termination = false
    snapshot_id = "snap-09a452d0f31e793c5"
  }
}

# Define the build steps to install dependencies and copy the app
build {
  # Reference the source from above
  sources = ["source.amazon-ebs.ubuntu"]


  provisioner "file" {
    source      = "./amazon-cloudwatch-agent.json"
    destination = "/home/ubuntu/amazon-cloudwatch-agent.json"
  }

  provisioner "shell" {
    script = "./amazon-cloudwatch-agent-setup.sh"
  }
}