import numpy as np
import os
import re

from keras import backend as K
from keras.models import load_model
from keras.preprocessing.image import img_to_array, load_img

img_width, img_height = 150, 150

if K.image_data_format() == 'channels_first':
	input_shape = (3, img_width, img_height)
else:
	input_shape = (img_width, img_height, 3)

model = load_model('part1.hdf5')

test_image_directory = '../test'

def natural_sort_key(s, _nsre=re.compile('([0-9]+)')):
    return [int(text) if text.isdigit() else text.lower()
            for text in _nsre.split(s)]    

images_array = []
for file in sorted(os.listdir(test_image_directory), key=natural_sort_key):
	if file.endswith('.jpg'):
		image_path = test_image_directory + '/' + file
		image = load_img(image_path, False, target_size=(img_width, img_height))
		image = img_to_array(image)
		image = image / 255
		images_array.append(image)

images = np.stack(images_array)
predictions = model.predict(images).flatten()
np.savetxt('part1-results.txt', predictions)
