# 导入相关库
import os
from lxml import etree
from tqdm import tqdm


def voc2txt():
    # 获取xml文件夹下的所有xml文件名，存入列表
    xmls_list = os.listdir(xmls_ori_path)
    for xml_name in tqdm(xmls_list):
        # 打开写入文件
        txt_name = xml_name.replace('xml', 'txt')

        f = open(os.path.join(txts_save_path, txt_name), 'w')  # 代开待写入的txt文件
        with open(os.path.join(xmls_ori_path, xml_name), 'rb') as fp:
            # 开始解析xml文件
            xml = etree.HTML(fp.read())
            width = int(xml.xpath('//size/width/text()')[0])
            height = int(xml.xpath('//size/height/text()')[0])
            # 获取对象标签
            obj = xml.xpath('//object')
            for each in obj:
                name = each.xpath("./name/text()")[0]
                classes = dic[name]
                xmin = int(each.xpath('./bndbox/xmin/text()')[0])
                xmax = int(each.xpath('./bndbox/xmax/text()')[0])
                ymin = int(each.xpath('./bndbox/ymin/text()')[0])
                ymax = int(each.xpath('./bndbox/ymax/text()')[0])
                # 归一化
                dw = 1 / width
                dh = 1 / height
                x_center = (xmin + xmax) / 2
                y_center = (ymax + ymin) / 2
                w = (xmax - xmin)
                h = (ymax - ymin)
                x, y, w, h = x_center * dw, y_center * dh, w * dw, h * dh
                # 写入
                f.write(str(classes) + ' ' + str(x) + ' ' + str(y) + ' ' + str(w) + ' ' + str(h) + ' ' + '\n')
        f.close()  # 关闭txt文件


if __name__ == '__main__':
    dic = {'key': "0",  # 创建字典用来对类型进行转换
           'cube': "1",   # 此处的字典要与自己的classes.txt文件中的类对应，且顺序要一致
           'book': "2",
           
           }

    xmls_ori_path = r"C:/Users/ASUS/Desktop/dataset/increase/label"         # xml文件所在的文件夹
    txts_save_path = r"C:/Users/ASUS/Desktop/dataset/increase/out"         # txt文件所在的文件夹

    os.mkdir(txts_save_path) if not os.path.exists(txts_save_path) else None

    voc2txt()
    
