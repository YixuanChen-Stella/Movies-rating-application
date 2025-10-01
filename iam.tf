# resource "aws_iam_role" "webapp_role" {
#   name = "webapp-role"
#
#   assume_role_policy = jsonencode({
#     Version = "2012-10-17"
#     Statement = [{
#       Effect = "Allow"
#       Principal = {
#         Service = "ec2.amazonaws.com"
#       }
#       Action = "sts:AssumeRole"
#     }]
#   })
# }
#
# resource "aws_iam_instance_profile" "csye6225_instance_profile" {
#   name = "csye6225_instance_profile"
#   role = aws_iam_role.webapp_role.name
# }
#
# resource "aws_iam_role_policy_attachment" "cloudwatch" {
#   role       = aws_iam_role.webapp_role.name
#   policy_arn = "arn:aws:iam::aws:policy/CloudWatchAgentServerPolicy"
# }
#
# resource "aws_iam_role_policy_attachment" "ssm" {
#   role       = aws_iam_role.webapp_role.name
#   policy_arn = "arn:aws:iam::aws:policy/AmazonSSMManagedInstanceCore"
# }
