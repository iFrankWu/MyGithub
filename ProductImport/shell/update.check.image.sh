#!/bin/bash

#同步 缩略图
 rsync -avz /mnt/wedding/thumb/  wxapache2@74.55.14.218:/home/wxapache2/9dresses_file/thumb；

#同步 小图
 rsync -avz /mnt/wedding/small  wxapache2@74.55.14.218:/home/wxapache2/9dresses_file/small；

#同步 主图

rsync -avz /mnt/wedding/qualified_image_new/ wxapache2@74.55.14.218:/home/wxapache2/9dresses_file/qualified_image_new/


#同步水印图
rsync -avz /mnt/wedding/qualified_image_water/ wxapache2@74.55.14.218:/home/wxapache2/9dresses_file/qualified_image_water/
