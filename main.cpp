#include <cstdio>
#include <iostream>
#include <vector>
#include <unordered_map>
#include <cmath>

using namespace std;

struct vec_hasher {
    int operator()(const vector<int> &vec) const {

		int hash = vec[0];
		for(auto &i : vec) {
			hash ^= i + 0x9e3779b9 + (hash >> 2) + (hash << i);
		}
		return hash;
	}
};

int get_max_col_index(const vector<int> &problem) {
	int max_val = 0, max_ix = 0, sz = problem.size(), current = 0;
	for (int i = sz - 1; i >= 0; i--) {
		current = problem[i];
		if (current > max_val) {
			max_val = current;
			max_ix = i;
		}
	}
	return max_ix;
}

void remove_sq(vector<int> &problem, int l, int sqsz) {
	int new_col = problem[l] - sqsz;
	for (int i = 0; i < sqsz; i++) {
		problem[l - i] = new_col;
	}
}
unsigned long long int solve(
		vector<int> &problem, unordered_map<vector<int>,
		unsigned long long, vec_hasher> &mem) {

	unsigned long long res = 0;

	try {
		return mem.at(problem);
	} catch (out_of_range &e) {
		//	do nothing
	}

	int max_col_line = get_max_col_index(problem);
	int max_col = problem[max_col_line];

	if (max_col <= 1) {
		return 1;
	}

	vector<int> sub_problem;
	int sqsz = 1;

	for (int i = max_col_line;
			problem[i] == max_col && sqsz <= max_col; i--, sqsz++) {

		sub_problem = problem;
		remove_sq(sub_problem, max_col_line, sqsz);
		res += solve(sub_problem, mem);
	}

	mem[problem] = res;

	return res;
}

int main() {
	vector<int> problem;
	int n, m, input;
	bool is_nonzero = false;
	unordered_map<vector<int>, unsigned long long, vec_hasher> mem(6291469);
	
	cin >> n;
	cin >> m;

	for (int i = 0; i < n; i++) {
		cin >> input;
		problem.push_back(input);
		if (input != 0) {
			is_nonzero = true;
		}
	}
	if (!is_nonzero) {
		cout << 0 << "\n";
		return 0;
	}

	cout << solve(problem, mem) << "\n";
	return 0;
}