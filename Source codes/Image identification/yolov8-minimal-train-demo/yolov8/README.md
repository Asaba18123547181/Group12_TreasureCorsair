# Yolov8 最小训练示例

这是一个最基础的 `yolov8` 训练示例，运行 `python train.py` 即可开始训练，然后在 `runs` 目录下查看训练结果

`datasets/demo` 里面是一个示例数据集，需要将 `train/images` 和 `train/labels` 替换成自己标注的数据，同时在 `valid/images` 和 `valid/labels` 里面也要放置一些数据用于验证（这是训练过程中选择最优模型的依据）

对于更加复杂的训练，可参考 [ultralytics 的官方文档](https://docs.ultralytics.com/modes/train/)
