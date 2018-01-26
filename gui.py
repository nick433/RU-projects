from tkinter import *
from math import sin, sqrt, ceil, floor, fabs
import re
import string

WIDTH, HEIGHT = 1100, 690
global px,py
window = Tk()


def leftClick(event):

	#print(event.x)
	#print(event.y)
	px = event.x
	py = event.y
	x = floor(px/5)-8  
	y = fabs(ceil(py/5)-124) #MATH MAGIC YAY PIXELS
	data = ""
	if( x >= 0 and x <= 159 and y>=0 and y<=119):
		data = "selected: (" + str(x) + "," + str(int(y)) + ")"
		label.config(text = data)
		
	
#def drawf():



	#20 40 would be
def drawAt(img,x,y,type): #img is the image to be painted on, x,y are the coordinates, type will be denoted by 0,1,2,a or b
	if(y<=59): #sep from 0 to 59 and 60 to 119
		y = 119-y;
	else: 
		y = abs(y - 119);
	x = x*5+40;
	y = y*5;
	if(type == '2'): #hard to traverse will be blue
		
		i = x
		j = y
		while(i<x+4):
			while(j<y+4):
				img.put("blue",(i,HEIGHT-70-j))
				j = j + 1
			
			j = y
			i = i + 1
	if(type == '1'): 
		pass
		#keep as default color for these spots
		
	elif(type == '0'): #hard to traverse will be blue
		
		i = x
		j = y
		while(i<x+4):
			while(j<y+4):
				img.put("black",(i,HEIGHT-70-j))
				j = j + 1
			#print("i" + str(i))
			j = y
			i = i + 1
	elif(type == 'a'): #hard to traverse will be blue
		
		i = x
		j = y
		while(i<x+4):
			while(j<y+4):
				img.put("cyan",(i,HEIGHT-70-j))
				j = j + 1
			#print("i" + str(i))
			j = y
			i = i + 1
	elif(type == 'b'): #hard to traverse will be blue
		
		i = x
		j = y
		while(i<x+4):
			while(j<y+4):
				img.put("violet",(i,HEIGHT-70-j))
				j = j + 1
			#print("i" + str(i))
			j = y
			i = i + 1
	elif(type == 's'): #hard to traverse will be blue
		print("in s")
		i = x
		j = y
		while(i<x+4):
			while(j<y+4):
				img.put("red",(i,HEIGHT-70-j))
				j = j + 1
			#print("i" + str(i))
			j = y
			i = i + 1
	
	#check for A* path coordinates somewhere here
	return
	
with open('map1-Astar.txt') as f:
	lines = f.readlines()
f.close()
f = open("map1.txt","r")

print()
print("LOADING GUI.....")
l = (re.findall('\d+', f.readline()))
#print(l[0] + ", " + l[1] + "\n")
startx = l[0]
starty = l[1]
l = (re.findall('\d+', f.readline()))
#print(l[0] + ", " + l[1] + "\n")
goalx = l[0]
goaly = l[1]
f.readline() 
f.readline() 
f.readline() #difficult to traverse centers
f.readline() 
f.readline() 
f.readline() 
f.readline() 
f.readline()


window.title("le gooey")
window.geometry("1100x690")
window.resizable(width = False,height = False)
message = "Click a tile! (or don't)"
label = Label(window,text=message, font='size, 18')
label.place(relx=0.05,rely=0.95)
label.pack()

frame = Frame(window, width=WIDTH/2,height = HEIGHT/2)
#frame.bind("<Button-1>",leftClick)
frame.pack()

#c1 = tk.Canvas(frame, ...)
#c2 = tk.Canvas(f, ...)
#c1.pack(side="left", fill="both", expand=True)
#c2.pack(side="right", fill="both", expand=True)

canvas = Canvas(frame, width=WIDTH, height=HEIGHT)
canvas.bind("<Button-1>",leftClick)
#ImgPanel.bind('<button-1>',onmouse)
canvas.pack(fill="both",expand=True)
img = PhotoImage(width=WIDTH, height=HEIGHT)
canvas.create_image((WIDTH/2, HEIGHT/2), image=img, state="normal")
i = 0
j = 0

while(i<120):
	line = f.readline()
	while(j<160):
		#print(line[j], end='')
		drawAt(img,j,i,line[j])
		j = j + 1

	#print(end='\n');
	j = 0
	i = i + 1
	
for line in lines:
	list = [int(k) for k in line.split(' ')]

	print (list)
	x = int(list[0])
	y = int(list[1])
	x = fabs(119-x)
	y = fabs(y)
	print(x)
	print(y)
	
	drawAt(img,int(y),int(x),'s')	


x = 0
y = 0
#	for x in range(WIDTH):
#if(x<200):



#drawAt(img,20,40,'2')



#elif(x>HEIGHT):
#	pass	
#else:
#img.put("green", (10,25))
#y+=1

mainloop()

f.close()
	
	
print()