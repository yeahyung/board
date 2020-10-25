#!/bin/bash
source /Users/user/opt/anaconda3/etc/profile.d/conda.sh
conda activate base
python /Users/user/ncp/board/src/main/resources/static/python/train_val-local.py

python /Users/user/ncp/yolov5/train.py --img 416 --batch 16 --epochs 50 --data /Users/user/trainImage/testDir/data.yaml --cfg /Users/user/ncp/yolov5/models/yolov5s.yaml --weights /Users/user/ncp/yolov5/yolov5s.pt --name mask_yolov5s_results

