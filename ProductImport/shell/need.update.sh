#!/bin/bash

ll -R /home/wxapache2/9dresses_file/qualified_image_water/ | grep -e "^-" | awk '{print $9}' > /tmp/water.file.list 
ll -R /home/wxapache2/9dresses_file/qualified_image_new/ | grep -e "^-" | awk '{print $9}' > /tmp/no_water.file.list  

echo "sdiff /tmp/water.file.list /tmp/no.water.file.list";

sdiff -s /tmp/water.file.list /tmp/no_water.file.list