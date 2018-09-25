#include<iostream>
#include<queue>
#include<list>
#include"board.h"
using namespace std;

class Comp
{
public:
	bool operator () (int lhs, int rhs) {
		return lhs > rhs;
	}
};

class Config{
private:
	
public:
	Board board;
	Config *predecessor;
	int cost;
	int manhattan;
	Config() {}
	Config(Board _board, Config *_predecessor, int _cost){
		board = _board;
		predecessor = _predecessor;
		cost = _cost;
		manhattan = board.manhattan();
	}
	Config(Config *_config) {
		board = _config->board;
		predecessor = _config->predecessor;
		cost = _config->cost;
		manhattan = _config->manhattan;
	}
	int compareTo(Config _config){
		return (manhattan + cost) - (_config.manhattan + _config.cost);
	}
};

class Solver{	
	Config finalConfig;
	bool solutionFound;
public:
	Solver() {}
	Solver(Board initial) {
		priority_queue<Config,Comp> gameTree;
		gameTree.push(Config(initial, NULL , 0));
		priority_queue<Config,Comp> gameTreeTwin;
		gameTree.push(Config(initial.twin, NULL , 0));
		while(true){
			if(gameTree.empty()) break;
			Config top = gameTree.top;
			if(top.board.isGoal()){
				finalConfig = top;
				solutionFound = true;
				break;
			}
			for (auto i=top.board.neighbors().begin ; i!=top.board.neighbors().end; )
			{
				Board nb = *i;
				if(top.predecessor==NULL || (!nb.equals(top.predecessor->board)) ) {
					Config *topPointer = &top;
					Config next = new Config(nb, topPointer , top.cost+1);
					gameTree.push(next);
				}		
			}
			if(!gameTreeTwin.empty){
				top =gameTreeTwin.top;
				//
				if(top.board.isGoal()){
					//
					break;
				}
				for (auto i=top.board.neighbors.begin ; i!=top.board.neighbors().end; )
				{
					Board nb = *i;
					if(top.predecessor==NULL || (!nb.equals(top.predecessor->board)) )   {
						Config *topPointer = &top;
						Config next = new Config(nb, topPointer , top.cost+1);
						gameTree.push(next);
					}		
				}
				//
			}
			//
		
		}

	}
	bool isSolvable(){
			return solutionFound;
	}
	int moves(){
		if(!solutionFound) return -1;
		return finalConfig.cost;
	}
	vector<Board> solution(){
		if(!solutionFound) return vector<Board>();
		vector<Board> solutionList;
		Config config = finalConfig;
		while (config.predecessor!=NULL) {
			solutionList.push_back(config.board);
			config = *config.predecessor;
		}
		return solutionList;
	}

};

int main(){
	cout<<"Hello World!";
}