from glob import glob
from sklearn.model_selection import train_test_split
import zipfile
import os
import yaml

fantasy_zip = zipfile.ZipFile('/root/pythonDir/test.zip')
test_paths = "/root/pythonDir/testDir"
if not(os.path.isdir(test_paths)):
    os.makedirs(test_paths)
fantasy_zip.extractall(test_paths)

img_list = glob(test_paths + "/images/*.jpg")
train_img_list, val_img_list = train_test_split(img_list, test_size=0.2, random_state=2000)

with open(test_paths + '/train.txt', 'w') as f:
    f. write('\n'.join(train_img_list) + '\n')
with open(test_paths + '/valid.txt', 'w') as f:
    f.write('\n'.join(val_img_list) + '\n')

with open(test_paths + '/data.yaml', 'r') as f:
    data = yaml.load(f, Loader=yaml.FullLoader)
data['train'] = test_paths + '/train.txt'
data['val'] = test_paths + '/valid.txt'
with open(test_paths + '/data.yaml', 'w') as f:
    yaml.dump(data, f)