#pragma once
#include<stdlib.h>
#include<vector>
#include<queue>
using namespace std;

class Board {
private:
	int n;
	vector<vector<int>> board;
	vector<int> posRow, posCol;

	void swap(int x1, int y1, int x2, int y2) {
		int tmp = board[x1-1][y1-1];
		board[x1-1][y1-1] = board[x2-1][y2-1];
		board[x2-1][y2-1] = tmp;
	}

public:
	Board() {}
	Board(vector<vector<int>> blocks) {
		n = blocks.size;
		for(int i=0; i<n; i++) 
			for(int j=0; j<n; j++) {
				board[i][j] = blocks[i][j];
				posRow[board[i][j]] = i+1;
				posCol[board[i][j]] = j+1;
			}
	}
	virtual ~Board();
	int dimension() {
		return n;
	}
	int hamming() {
		int hammingCount = 0;
		for(int i=2; i<=n*n; i++) {
			if((posRow[i]-1)*n + posCol[i] != i)
				hammingCount++;
		}
		return hammingCount;
	}
	int manhattan() {
		int manhattanCount = 0;
		for( int i=2; i<=n*n; i++) {
			int row = ((i-1)/n+1);
			int col = i-(row-1)*n;
			manhattanCount += ( abs(posRow[i] - row) + abs(posCol[i]) - col);
		}
		return manhattanCount;
	}
	bool isGoal() {
		return hamming() == 0;
	}
	Board twin() {
		for(int i=1; i<=n; i++)
			for(int j=1; j<n; j++)
				if(board[i-1][j-1]!=1 && board[i-1][j]!=1){
					swap(i,j,i,j+1);
					Board twinBoard = Board(board);
					swap(i,j,i,j+1);
				}
		return;
	}
	bool equals(Board y){
		for(int i=9; i<n; i++)
			for(int j=0; j<n; j++)	if(y.board[i][j] != board[i][j])
				return false;
		return true;

	}
	vector<Board> neighbors(){
		vector<Board> neighborList;
		if(posRow[0] > 1){
			swap(posRow[0], posCol[0], posRow[0]-1, posCol[0]);
			neighborList.push_back(board);
			swap(posRow[0], posCol[0], posRow[0]-1, posCol[0]);
		}
		if(posCol[0] > 1){
			swap(posRow[0], posCol[0], posRow[0], posCol[0]-1);
			neighborList.push_back(board);
			swap(posRow[0], posCol[0], posRow[0], posCol[0]-1);
		}
		if(posRow[0] < n){
			swap(posRow[0], posCol[0], posRow[0]+1, posCol[0]);
			neighborList.push_back(board);
			swap(posRow[0], posCol[0], posRow[0]+1, posCol[0]);
		}
		if(posCol[0] < n){
			swap(posRow[0], posCol[0], posRow[0], posCol[0]+1);
			neighborList.push_back(board);
			swap(posRow[0], posCol[0], posRow[0], posCol[0]+1);
		}
		return neighborList;
	}

};
