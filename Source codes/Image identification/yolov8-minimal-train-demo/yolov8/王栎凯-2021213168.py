# 导入random模块
import random

# 输入N和M的值
N = int(input("Input: N\n"))88
M = int(input("Input: M\n"))

# 检查N和M是否满足条件
if N > 1 and N <= 100 and M > 1 and M <= N:
    # 生成一个有N个元素的列表，每个元素是一个随机数n
    list_n = [random.randint(1, 2**31 - 1) for _ in range(N)]
    # 从这个列表中随机取出M个数
    list_m = random.sample(list_n, M)
    # 对这M个数字进行排序
    list_m.sort()
    # 显示排序后的这M个数字
    print("Output:", list_m)
else:
    # 如果N和M不满足条件，打印错误信息
    print("Invalid input. Please make sure that 1 < N <= 100 and 1 < M <= N.")
