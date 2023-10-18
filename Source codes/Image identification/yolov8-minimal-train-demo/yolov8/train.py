from ultralytics import YOLO
def main():
     model = YOLO('C:/Users/ASUS/Desktop/miniterm/yolov8-minimal-train-demo/yolov8/final.pt')

     model.train(
     data = 'C:/Users/ASUS/Desktop/miniterm/demo/data.yaml',
     epochs = 115,
     imgsz = 640,
     device=[0],
     batch=6
)

if __name__ == '__main__':
    main()