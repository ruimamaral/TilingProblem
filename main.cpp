#include <cstdio>
#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

struct vector_hasher {
int operator()(const vector<int> vec) const {
		int hash = vec[0] + 11;
		for(auto &i : vec) {
			hash ^= i + 0x9e3779b9 + (hash << 7);
		}
		return hash;
	}
};

int get_max_col_index(const vector<int> *problem) {
	int max_val = 0, max_ix = 0, sz = problem->size(), current = 0;
	for (int i = 0; i < sz; i++) {
		current = (*problem)[i];
		if (current > max_val) {
			max_val = current;
			max_ix = i;
		}
	}
	return max_ix;
}

void remove_sq(vector<int> *problem, int l, int sqsz) {
	int new_col = (*problem)[l] - sqsz;
	for (int i = 0; i < sqsz; i++) {
		(*problem)[l + i] = new_col;
	}
}
unsigned long long int solve(vector<int> *problem,
		unordered_map<vector<int>, unsigned long long, vector_hasher> &mem) {

	unsigned long long res = 0;

	try {
		return mem.at(*problem);
	} catch (out_of_range &e) {
		//	do nothing
	}

	int max_col_line = get_max_col_index(problem);
	int max_col = (*problem)[max_col_line];

	if (max_col <= 1) {
		return 1;
	}

	vector<int> *sub_problem;
	vector<vector<int>*> sub_problems;
	int sqsz = 1;

	for (int i = max_col_line;
			(*problem)[i] == max_col && sqsz <= max_col; i++, sqsz++) {

		sub_problem = new vector<int>;
		(*sub_problem) = (*problem);
		remove_sq(sub_problem, max_col_line, sqsz);
		sub_problems.push_back(sub_problem);
		res += solve(sub_problem, mem);
		free(sub_problem);
	}

	mem[*problem] = res;

	return res;
}

int main() {
	vector<int> *problem = new vector<int>;
	int n, m, input;
	bool is_nonzero = false;
	unordered_map<vector<int>, unsigned long long, vector_hasher> mem;
	
	cin >> n;
	cin >> m;

	for (int i = 0; i < n; i++) {
		cin >> input;
		(*problem).push_back(input);
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