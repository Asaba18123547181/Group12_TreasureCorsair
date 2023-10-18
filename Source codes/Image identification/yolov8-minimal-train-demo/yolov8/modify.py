import os

folder_path = "C:/Users/ASUS/Desktop/output/label"  # labels文件夹路径

# C:\Users\ASUS\Desktop\dataset\book.v1i.yolov8\valid\labels
# 获取labels文件夹中所有文件的文件名
file_names = os.listdir(folder_path)

# 遍历每个文件，修改每一行的第一个数字为1
for file_name in file_names:
    file_path = os.path.join(folder_path, file_name)
    
    # 打开文件并读取内容
    with open(file_path, 'r') as file:
        lines = file.readlines()
    
    # 修改每一行的第一个数字为1
    modified_lines = []
    for line in lines:
        line_parts = line.split()
        line_parts[0] = '1'
        modified_line = ' '.join(line_parts)
        modified_lines.append(modified_line)
    
    # 将修改后的内容写入文件
    with open(file_path, 'w') as file:
        file.writelines(modified_lines)
