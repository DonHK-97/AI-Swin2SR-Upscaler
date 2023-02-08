from transformers import AutoImageProcessor, Swin2SRForImageSuperResolution
from PIL import Image
import numpy as np
import torch
import sys

# tokenizer = AutoImageProcessor.from_pretrained("caidas/swin2SR-lightweight-x2-64")
# model = Swin2SRForImageSuperResolution.from_pretrained("caidas/swin2SR-lightweight-x2-64")

# tokenizer.save_pretrained("./Upscaler/swin2sr_t0")
# model.save_pretrained("./Upscaler/swin2sr_t0")

path = sys.argv[1]
processor = AutoImageProcessor.from_pretrained("C:/G_Drive/Study/SF_Image/src/main/java/com/Don_Spring_Flask/SF_Image/PythonAI/Upscaler/swin2sr_t0")
model = Swin2SRForImageSuperResolution.from_pretrained("C:/G_Drive/Study/SF_Image/src/main/java/com/Don_Spring_Flask/SF_Image/PythonAI/Upscaler/swin2sr_t0")
model.eval()

inputs = processor(Image.open(path), return_tensors="pt")

with torch.no_grad():
    outputs = model(**inputs)

output = outputs.reconstruction.data.squeeze().float().cpu().clamp_(0, 1).numpy()
output = np.moveaxis(output, source=0, destination=-1)
output = (output * 255.0).round().astype(np.uint8)

renewed = path.replace(".jpg", "_RE.jpg")
Image.fromarray(output).save(renewed)
