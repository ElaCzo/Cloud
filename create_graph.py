import networkx as nx
import matplotlib.pyplot as plt
import random
from scipy import *

G=nx.Graph()

lignes=0
with open("jaccard.txt") as f:
    line = f.readline()
    while line:
        lignes+=1
        line = f.readline()
f.close()

G.add_nodes_from(range(lignes))

with open("jaccard.txt") as fp:
	line = fp.readline()
	num_ligne=0
	while line:
		tab_p2_l = line.split(" ")
		for it in range(0, len(tab_p2_l), 2):
			if it > (len(tab_p2_l)-2):
				break
			i=num_ligne
			j=int(tab_p2_l[it])
			it+=1
			k=double(tab_p2_l[it])
			if i < j:
				G.add_edge(i, j, length=k)
		num_ligne+=1
		line = fp.readline()

fp.close()
nx.draw_networkx(G, node_size=15, node_color='c', node_shape='h', with_labels=False)
plt.show()
