
 import java.util.*;
import java.util.Hashtable;
import java.util.Arrays;
import java.lang.Math;


public class fingerTable {
	int[][] fingerTable = new int [5][2];
	int addr;
	public fingerTable( ) {
		 
	}
	
	public void setAddr(int x) {
		addr= x;
	}
	public int getAddr() {
		return addr;
	}

	public static void main(String[] args) {
		fingerTable[] ftArray;
		
		boolean check = false;
		Scanner kb = new Scanner(System.in);
		System.out.println("Please enter number of active nodes");
		int numNodes = kb.nextInt();
		 
		System.out.println("number of active nodes: "+numNodes);
	//	m = 32;
	//	c = 2;
	//	a = 0;
	//	x0= 3;
		ftArray = new fingerTable[numNodes];
	
 
	//Linear congruential generator for active node addresses
	int x0, m, c, a;
	int nodeAddr[];
	m = 32;
	c = 0;
	a = 2;
	x0= 3;
	
	
	int temp;
	nodeAddr = new int[ numNodes];
	nodeAddr[0]=x0;
	
	for(int i=1; i< numNodes; i++) {
		temp = nodeAddr[i-1];
		nodeAddr[i]=(a*temp+c)%m;
	}
	
	Arrays.sort(nodeAddr);
	for(int i=0;i<numNodes; i++) {
		ftArray[i]=new fingerTable();
	}
	for(int i=0; i<numNodes; i++) {
		ftArray[i].addr=nodeAddr[i];
	}

	int[] tempFT = new int[5];
	for(int i=0; i<numNodes; i++) {
		int pow= 0;
		for(int j=0; j<5; j++) {
			int result = (int)Math.pow(2,j);
			int start = (nodeAddr[i]+result)%32;
			
			tempFT[j]=start;
			ftArray[i].fingerTable[j][0]= start;
			
			//System.out.println(ftArray[i].fingerTable[j][0]);
			pow++;
		}
	}
	
 	
	// Add successor value to finger table using test method
	for(int i=0; i<numNodes; i++) {
		
		for(int j=0; j<5; j++) {
			 
			
			int val = ftArray[i].fingerTable[j][0];
			 
			int ftVal = test(val, nodeAddr);
			ftArray[i].fingerTable[j][1]=ftVal;
		}
		
	}
	for(int i=0; i<numNodes; i++) {
		Arrays.sort(ftArray[i].fingerTable, Comparator.comparingInt( b -> b[0]) );
	}
	System.out.println(Arrays.deepToString(ftArray[0].fingerTable));
	 
	for(int i=0; i<numNodes; i++) {
		System.out.println("Fingertable for "+nodeAddr[i]);
		for(int j= 0; j<5; j++) {
				
		System.out.println(ftArray[i].fingerTable[j][0]+" " +ftArray[i].fingerTable[j][1]);
		}
		System.out.println("........");
	}
	System.out.println("enter key to search");
	 
	int key = kb.nextInt();
	findSuccessor(key,nodeAddr, ftArray);
	
	
	}
	 
	
	
	
	
	public static void findSuccessor(int key, int nodes[], fingerTable ft[]) {
				int max  = nodes.length-1;
				boolean check = false;
				int i=0;
				int tableVal;
				int ind;
				if(key > nodes[0] && key< nodes[1]) {
					System.out.println("Key information stored in "+nodes[1]);
				}
				else {
					tableVal = checkTable(key,nodes,ft,i);
					ind = getIndex(nodes, tableVal);
					int temp;
					
					while(check==false) {
						if(nodes[ind-1]<key && key<nodes[ind]) {
							System.out.printf("INFORMATION STORED IN "+nodes[ind]+" SEARCH CONCLUDED");
							check = true;
						}
						if(tableVal==nodes[max] && tableVal != key) {
							System.out.println("information stored in "+nodes[0]);
							check =true; 
						 
						}
						
						if(tableVal==key)
						{
							System.out.println("node info stored in self/node: "+tableVal);
							check =true;
 						}
						 
						if(key > nodes[ind] && key< nodes[ind+1] ) {
							System.out.println("AT FINGERTABLE OF NODE "+nodes[ind]);
							System.out.println("KEY VALUE BETWEEN CURRENT NODE AND NEXT NODE");
							System.out.println("key information stored in "+nodes[ind+1]);
							check =true; 
 
						}
						
						if(check !=true){
							tableVal = checkTable(key,nodes, ft, ind); 
							ind = getIndex(nodes,tableVal);
							System.out.println(nodes[ind]);
							System.out.println("Going to finger table value: "+tableVal);
							
						}
					}
				}
	}
	public static int getIndex(int nodes[], int val) {
		int index=0;
 		for(int i=0; i<=nodes.length-1; i++) {
			if(nodes[i]==val) {
				index = i;
				
			}
		}
		return index;
	 
	}
	
	
	public static int checkTable(int key, int nodes[], fingerTable ft[], int index) {
		int table = 0;
		int max = nodes.length-1;
		int temp;
		if(index>0 && nodes[index]!=nodes[max]) {
			if(nodes[index]< key && nodes[index+1]>key) {
				return nodes[index+1];
			}
		}
 		for(int i=0; i<5; i++) {
			temp = ft[index].fingerTable[i][0];
			if(temp ==key) {
				table = ft[index].fingerTable[i][1];
				break;
			}
			 
			if(temp>key) {
				temp= ft[index].fingerTable[i-1][0];
				table = ft[index].fingerTable[i-1][1];
				System.out.println("Going to finger table of "+table);
				break;
			}
			
			if(temp < key && i==4) {
				table = ft[index].fingerTable[i][1];
				System.out.println("closest predecessor--- "+table);
				System.out.println("going to fingertable of "+table);
			}
			if(key == ft[index].fingerTable[i][1]) {
				
				table =  key;
				
				break;
			}
			 
		}
		 
		return table;
	}
	
	public static int test(int x, int arr[]) {
		boolean check = false;
		int val=0;
		int i=0;
		while(check == false) {
 			if(x< arr[0]) {
				 
				val = arr[0];
				check = true;
			}
			if(x>arr[arr.length-1]) {
				 
				val = arr[0];
				check = true;
			}
			if(x>arr[i] && x<arr[i+1] ) {
				 
				val = arr[i+1];
				check = true;
			}
			if(x == arr[i]) {
				val = arr[i];
				check = true;
			}
			
			if(x>arr[i] && arr[i] != arr[arr.length-1]) {
				i++;
			}
			 
		}
		return val;
	}
	
	
	
	
 
	
	 
}
