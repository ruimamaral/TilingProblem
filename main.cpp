#include <cstdio>
#include <iostream>
#include <vector>
#include <unordered_map>

using namespace std;

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
	for (int i = line + 1; (*problem)[i] == col; i++, sqsz++) {}
	return sqsz;
}

unsigned long long int solve(vector<int> *problem,
		unordered_map<int, unsigned long long> &mem) {

	// int problem_hash = hash_vec(problem);
	unsigned long long res = 1;

	int max_col_line = get_max_col_index(problem);
	int max_sqsz = get_max_sqsz(problem, max_col_line);
	vector<int> *sub_problem = new vector<int>; //maybe not needed
	vector<vector<int>*> sub_problems;

	/**try {
		res = mem.at(problem_hash);
	} catch (out_of_range &e) {
		cout << "br";
		// do nothing
	} **/

	for (int i = 1; i <= max_sqsz; i++) {
		(*sub_problem) = (*problem);
		remove_sq(sub_problem, max_col_line, i);
		sub_problems.push_back(sub_problem);
	}
	for (auto sp : sub_problems) {
		res += solve(sp, mem);
	}
	/*mem[problem_hash] = res; */

	return res;
}

int main() {
	vector<int> *problem = new vector<int>;
	int n, m, input;
	bool is_nonzero = false;
	unordered_map<int, unsigned long long> mem;
	
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

	printf("%d, %d\n", n, m);

	for (auto &i : (*problem)) {
		cout << i;
		cout << "\n";
	}

	cout << solve(problem, mem);
	return 0;
}

