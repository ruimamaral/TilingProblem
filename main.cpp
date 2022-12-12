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

int hash_vec(vector<int> *vec) {
	int hash = vec->size();
	for(auto &i : *vec) {
		hash += i * 6967;
	}
	return hash;
}

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

int get_max_sqsz(const vector<int> *problem, int line) {
	int sqsz = 1, col = (*problem)[line];
	for (int i = line + 1; (*problem)[i] == col && sqsz < col; i++, sqsz++) {}
	return sqsz;
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

	if ((*problem)[max_col_line] <= 1) {
		mem[*problem] = 1;
		return 1;
	}

	int max_sqsz = get_max_sqsz(problem, max_col_line);
	vector<int> *sub_problem;
	vector<vector<int>*> sub_problems;

	for (int i = 1; i <= max_sqsz; i++) {
		sub_problem = new vector<int>;
		(*sub_problem) = (*problem);
		remove_sq(sub_problem, max_col_line, i);
		sub_problems.push_back(sub_problem);
	}
	for (auto &sp : sub_problems) {
		res += solve(sp, mem);
		free(sp);
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
		cout << 0;
		return 0;
	}

	cout << solve(problem, mem) << "\n";
	return 0;
}

