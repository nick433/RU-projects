'''
Created on Sep 23, 2017

@author: nsm83, jhl119
'''
import numpy as np
import random
import itertools;
from copy import deepcopy
from math import exp
from operator import itemgetter;
import time

class Node:
    def __init__(self, value, y, x, visited=False, minDistance=-1, pathTo = [0,0], maxLegal = -1):
        self.value = value
        self.y = y
        self.x = x
        self.visited = visited
        self.minDistance = minDistance
        self.maxLegal = maxLegal
        
    def __repr__(self):
        return "%s" % id(self)
    def printPuz(self):
        pass
global puzSize

def main():
    puzSize = getSizeFromFile('puzzleSize.txt') # we assume you arent erasing our puzzleSize.txt
    puzzle = makePuzzle(puzSize)
    i = 0
    n=0
    step25 = 2
    step10 = 5
    step5 = 10
    totalStartKb = 0
    totalStepsb = 0
    totalEndKb = 0
    totalTimeb = 0
    
    totalStartKc = 0
    totalStepsc = 0
    totalEndKc = 0
    totalTimec = 0
    totalSteps = 0
    totalEndK = 0
    totalStartK = 0
    totalTime = 0
    avEndK = 0
    csv = ''
    csvb = ''
    csvc = ''
    i = 0
    a = 0
    b = 0
    c = 0
    a1 = 0
    b1 = 0
    c1 = 0
    a2 = 0
    b2 = 0
    c2 = 0
    prob = 0.1
    j = 0
    k = 0
    l = 0
    mm =0
    nn =0
    o =0
    p =0
    q =0
    r =0
    s =0
    t =0
    
    d = 0
    e = 0
    f = 0
    g = 0
    h = 0
    ii= 0
    iter = 50
    while(n<1): #change back 5
        i = 0
        while(i<50):
            
            a += simulatedAnnealing(100, 100, .05)/50
            
            b += simulatedAnnealing(100, 100, .35)/50
          
            c += simulatedAnnealing(100, 100, .65)/50
            
            d += simulatedAnnealing(100, 100, .95)/50
            
            e += simulatedAnnealing(500, 100, .05)/50
            
            f += simulatedAnnealing(500, 100, .35)/50
          
            g += simulatedAnnealing(500, 100, .65)/50
            
            h += simulatedAnnealing(500, 100, .95)/50
            
            ii += simulatedAnnealing(1000, 100, .05)/50
            
            j += simulatedAnnealing(1000, 100, .35)/50
          
            k += simulatedAnnealing(1000, 100, .65)/50
            
            l += simulatedAnnealing(1000, 100, .95)/50
            mm += simulatedAnnealing(2000, 100, .05)/50
            
            nn += simulatedAnnealing(2000, 100, .35)/50
          
            o += simulatedAnnealing(2000, 100, .65)/50
            
            p += simulatedAnnealing(2000, 100, .95)/50            
            #a1 += a[1]
            #a2 += a[2]
            #b1 += b[1]
            #b2 += b[2]
            #c1 += c[1]
            #c2 += c[2]
            i+=1
        #startK, highK, milisElapsed,i,steps)   -  restarts,k,steps,mili
        csv += str(a) + "," + str(b) + "," + str(c) + "," + str(d) + "\n" + str(e) + "," + str(f) + "," + str(g) + "," + str(h) + "\n" + str(ii) + "," + str(j) + "," + str(k) + "," + str(l) + "\n" + str(mm) + "," + str(nn) + "," + str(o) + "," + str(p) + "\n" + str(q) + "," + str(r) + "," + str(s) + "," + str(t) 
        
        #csvb += "0.5," + str(e/50) + "," + str(iter) + "\n"
        #csvc += "0.8," + str(h/50) + "," + str(iter) + "\n"
        step25 += 2
        step10 += 5
        step5 += 10
        print("av K: " + str(totalEndK/50))    
        n+=1
        iter+=50
        print("n= " + str(n))
        b = 0
        e = 0
        h = 0
        a1 = 0
        b1 = 0
        c1 = 0
        a2 = 0
        b2 = 0
        c2 = 0
        
    np.savetxt('Output.txt', [csv + csvb + csvc], fmt='%s')        
#return (startK, highK, milisElapsed,i,steps)


def getPuzzleFromFile(fileName):
    
    with open(fileName) as f:
        M = []
        for line in f:
            line = line.split() # to deal with blank 
            if line:            # lines (ie skip them)
                line = [int(i) for i in line]
                M.append(line)
    print(M)
    dim = M[0][0]
    puzzle = np.array([[Node(0,j,i) for j in range(dim)] for i in range(dim)])
    M.pop(0)
    print()
    
    print(M)
    m = dim -1

    for i in range(dim):
        for j in range(dim):
            maxX = max(abs(m-i),abs(i))
            maxY = max(abs(m-j),abs(j))
            legal = max(maxX,maxY)
            puzzle[i][j].maxLegal = legal
            puzzle[i][j].value =  M[i][j]
            
    return puzzle
    
    

def getSizeFromFile(f):
    with open(f, 'r') as file:
        line = file.readline()
    n = (int (line))
    return n
def makePuzzle(n):
    puzzle = np.array([[Node(0,j,i) for j in range(n)] for i in range(n)])
    m = n - 1
    for i in range(n):
        for j in range(n):
            maxX = max(abs(m-i),abs(i))
            maxY = max(abs(m-j),abs(j))
            legal = max(maxX,maxY)
            puzzle[i][j].maxLegal = legal
            puzzle[i][j].value =  random.randint(1,legal)
            #print(str(puzzle[i][j].x) + "," + str(puzzle[i][j].y) + " : " + str(puzzle[i][j].value))
    
    puzzle[n-1,n-1].value = 0
    return puzzle
    #print(np.matrix(puzzle))
def printPuzzle(puzzle, dist=False):
    n = puzzle.shape[0]
    if(dist==False):   
        for i in range(n):
            for j in range(n):
                print(str(puzzle[i][j].value), end='')
                if(j == n-1):
                    print('')
                else: 
                    if(puzzle[i][j].value < 10):
                        print('  ', end='')
                    else: print(' ', end='')
    else:   
        for i in range(n):
            for j in range(n):
                if(puzzle[i][j].minDistance == -1):
                    print("X", end='')
                
                else:
                    print(str(puzzle[i][j].minDistance), end='')
                if(j == n-1):
                    print('')
                else: 
                    if(puzzle[i][j].minDistance < 10):
                        print('  ', end='')
                    else: print(' ', end='')
                        
    print()

def BFStep(board, cell=[0,0]):
    out = [];
    dim = board.shape[0];
    val = board[cell[0],cell[1]].value;
    rlud = [[cell[1]+val, cell[1]-val],[cell[0]+val, cell[0]-val]];
    for thing in rlud[1]:
        if 0 <= thing and thing <dim:
            #print("thing: " + str(thing) + ", " + str(cell[1]))
            #print("vistited= " + str(board[thing, cell[1]].visited))
            if board[thing, cell[1]].visited == True:
                pass
            else:
                board[thing, cell[1]].visited = True;
                board[thing, cell[1]].minDistance = board[cell[0],cell[1]].minDistance+1;
                out.append([thing, cell[1]]);
        else:
            pass
    for item in rlud[0]:
        if 0 <= item and item <dim:
            
            if board[cell[0],item].visited == True:
                pass
            else:
                board[cell[0],item].visited = True;
                board[cell[0],item].minDistance=board[cell[0],cell[1]].minDistance+1
                out.append([cell[0],item]);
        else:
            pass
    
    return out


def BFSevaluate(board, start = [0,0]):
    dim = board.shape[0]
    currentNodes = [start];
    board[start[0],start[1]].minDistance = 0;
    board[start[0],start[1]].visited = True;
    stepsTaken = 0;
    lim = board.shape[0]^2
    while stepsTaken<2000:
        temp = [];
        for item in currentNodes:
            temp = temp + BFStep(board, item);
            #print(temp)
        #print(temp)
        currentNodes = temp;
        if currentNodes == []:
            break 
        else:
            pass
        stepsTaken += 1
    notVisits = 0    
    if(board[dim-1,dim-1].visited == True):
        return board[dim-1,dim-1].minDistance
    else:
        for i in range(dim):
            for j in range(dim):
                if(board[j,i].visited == False):
                    notVisits+= 1
        return -1*notVisits
def hillClimb(board):
    dim= board.shape[0]
    randX = board.shape[0]-1
    randY = board.shape[0]-1
    while(randX == dim-1 and randY == dim-1):
        randX = random.randint(0,dim-1)
        randY = random.randint(0,dim-1)
    #print(str(randX) + ", " + str(randY))
    #print(board[randX,randY].maxLegal)
    oldVal = board[randX,randY].value
    board[randX,randY].value = random.randint(1,board[randX,randY].maxLegal)    
    while(oldVal == board[randX,randY].value):
        board[randX,randY].value = random.randint(1,board[randX,randY].maxLegal)  
    for i in range(dim):
        for j in range(dim):
            board[i][j].visited = False
            board[i][j].minDistance = -1
    #printPuzzle(board) 
def hillClimbEval(n,board = None):
    #k = BFS
    #n is iterations
    
    if(board is None):
        goodInput = False
        while(goodInput == False):
            goodInput = True
            x = input('\nEnter a size for the puzzle, or 0 to use size in puzzleSize.txt\n')
            if(x.isdigit()):
                a = int(x)
                if(a in [5, 7, 9, 11]):
                    board = makePuzzle(a)
                elif(a == 0):
                    puzSize = getSizeFromFile('puzzleSize.txt')
                    board = makePuzzle(puzSize)
                else: 
                    goodInput = False
            else: goodInput = False        
    else: 
        pass      
    startTime = int(round(time.time() * 1000))
    k = BFSevaluate(board) 
    startK = deepcopy(k)
    oldestBoard = deepcopy(board)    
    oldK = k
    l = [k]
    i = 0
    AS = 0
    while(i<n):
        oldBoard = deepcopy(board)
        hillClimb(board)
        k = BFSevaluate(board)
        if(k>=oldK):
            AS+=1
            oldK = k
            l.append(k)
            #print(list)
        else: board = oldBoard
        i+=1
        
    
    #print("Max min variance: " + str(l[-1]) + " - " + str(l[0]) + " = " + str(l[-1]-l[0])+ "\n")
    endTime = int(round(time.time() * 1000))
    milisElapsed = endTime - startTime
    return (AS,startK,l[-1],milisElapsed,i,board,l)

def restartClimb(starts, steps):
    puzSize = 7
    startTime = int(round(time.time() * 1000))    
    board = makePuzzle(puzSize);
    bestBoard = deepcopy(board);
    k = BFSevaluate(board);
    startK = deepcopy(k)
    oldestBoard = deepcopy(board);
    highK=k;
    AS = 0
    i = 0
    l = [];
    while starts > 0:
        j = hillClimbEval(steps, board);
        i+=steps
        k = j[2];
        l.append(j[-1]);
        if k >= highK:
            bestBoard = deepcopy(j[-2]);
            board = makePuzzle(puzSize);
            highK = k;
            AS+=1
        starts -= 1;
    endTime = int(round(time.time() * 1000))
    milisElapsed = endTime - startTime
    return (startK, highK, milisElapsed,i,steps)

def hillClimbRW(steps, prob, board = None):
    #add ssame stuff as start of the hillClimbRandom
    board=makePuzzle(11)
    steps+=1
    k = BFSevaluate(board)
    oldestBoard = deepcopy(board)
    oldK = k
    l = [k]
    i = 0
    m = 0
    while(m<steps-1):
        oldBoard = deepcopy(board)
        hillClimb(board)
        k = BFSevaluate(board)
        if(k>=oldK):
            i+=1;
            l.append(k);
            oldK = k;
            #print(list)
        else:
            r = random.uniform(0,1);
            if r <= prob:
                i+=1;
                l.append(k);
                oldK = k;
            else:
                board = oldBoard
        m+=1
    print()
    print("Original board:\n")
    printPuzzle(oldestBoard)
    print("Original board's min distances from start:\n")
    printPuzzle(oldestBoard,True)
    print("Newest board:\n")    
    printPuzzle(board)
    print("Newest board's min distances from start:\n")
    printPuzzle(board,True)
    print()
    print("Every iteration's mininum distance to goal:")
    print(l)
    print("\nlength: " + str(len(l)) + ". Total tries: " + str(m) + ".")
    print()
    return (l[-1])  
 
def simulatedAnnealing(initTemp, steps, decay, board=0):
    board = makePuzzle(11)
    k = BFSevaluate(board);
    oldestBoard = deepcopy(board);
    oldK = k;
    l =[k];
    T = initTemp;
    i=0;
    m=0;
    output = '50'
    while m <= steps:
        #if i > 13:
            #print("k = " + str(k) + " oldK = " + str(oldK) + " temp = " + str(T) + "  last 10: " + str(l[-10:]) + " m: " + str(m) + " i: " + str(i))
        oldBoard = deepcopy(board)
        hillClimb(board)
        k = BFSevaluate(board)
        if(k>=oldK):
            i+=1 #we only iterate when we get the same or better K
            l.append(k)
            oldK = k
            #print(list)
        else:
            r = random.uniform(0,1); 
# 2000 iters, 1000 temp, .995 rate, try .999 next since after 55k iterations we are at 1e-120            
            if(T>0):         
                if((k-oldK)/T < 709.7827): #overflow error from exp(710+)  
                    if r <= exp((k-oldK)/T):
                        i+=1
                        l.append(k)
                        oldK = k
                    else:
                        board = oldBoard
                else: 
                    if r <= exp(709.7827):
                        i+=1
                        l.append(k)
                        oldK = k
                    else:
                        board = oldBoard
            else:
                board = oldBoard
        T*=decay;
        m+=1;
    
    return l[-1]

def boardRefresh(board):
    dim = board.shape[0];
    for i in range(dim):
        for j in range(dim):
            board[i][j].visited = False
            board[i][j].minDistance = -1

def crossover(board, board2):
    dim = board.shape[0];
    outA = np.array([[Node(0,y,x) for y in range(dim)] for x in range(dim)])
    outB = np.array([[Node(0,y,x) for y in range(dim)] for x in range(dim)])
    split = int(dim/2) + 1;
    for i in range(dim):
        for j in range(split):
            maxX = max(abs(m-i),abs(i))
            maxY = max(abs(m-j),abs(j))
            legal = max(maxX,maxY)
            outA[i][j].maxLegal = legal
            outB[i][j].maxLegal = legal            
            outA[i][j].value = board.value
            outB[i][j].value = board2.value
        for j in range(split, dim):
            maxX = max(abs(m-i),abs(i))
            maxY = max(abs(m-j),abs(j))
            legal = max(maxX,maxY)
            outA[i][j].maxLegal = legal
            outB[i][j].maxLegal = legal            
            outA[i][j].value = board2.value
            outB[i][j].value = board.value
    return (outA, outB)

def selection(gen, num, selPressure):
    l = len(gen);
    c = 0;
    gen.sort(key=itemgetter(0));
    #while 
        
        
        

def geneticAlgorithm(steps, pop = 4, selPressure = 0.5, mutProb = 1):
    pass


    


if __name__ == "__main__":
    main()