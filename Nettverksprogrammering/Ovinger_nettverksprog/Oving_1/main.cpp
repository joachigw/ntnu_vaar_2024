#include <iostream>
#include <thread>
#include <vector>
#include <mutex>

using namespace std;

mutex primes_mutex;

/**
 * Check if the specified number is a prime.
 * @param number number to check if is a prime.
 * @return true if prime and false otherwise.
 */
bool is_prime(int number) {
    if (number == 0 || number == 1)
        return false;

    for (int i = 2; i <= (number/2); ++i) {
        if (number % i == 0)
            return false;
    }

    return true;
}


/**
 * Check every number in an interval and add every prime number to the specified vector, if any are found.
 * @param prime_numbers the vector to add prime numbers in.
 * @param start start of interval.
 * @param end end of interval.
 */
void primes_in_range(vector<int> &prime_numbers, int start, int end) {
    for (int i = start; i <= end; ++i) {
        if (is_prime(i)) {
            primes_mutex.lock();
            prime_numbers.push_back(i);
            primes_mutex.unlock();
        }
    }
}


int main() {
    vector<int> prime_numbers;
    int interval_start, interval_end, interval_size, n_threads;
    vector<thread> threads;

    // Retrieve input from user
    cout << "Interval start: ";
    cin >> interval_start;
    cout << "Interval end: ";
    cin >> interval_end;
    cout << "Enter amount of threads: ";
    cin >> n_threads;

    interval_size = (interval_end - interval_start + 1) / n_threads;

    // Create threads with responsibility of finding primes in a specified sub-interval
    for (int i = 0; i < n_threads; ++i) {
        int thread_start = interval_start + (i * interval_size);
        int thread_end = (i == (n_threads - 1)) ? interval_end : (thread_start + interval_size - 1);

        threads.emplace_back(primes_in_range, ref(prime_numbers), thread_start, thread_end);
    }


    for (int i = 0; i < n_threads; ++i) {
        threads[i].join();
        cout << "   ->Thread " << (i + 1) << " joined successfully." << endl;
    }


    if (prime_numbers.empty()) {
        cout << "There are no prime numbers in the range entered.";
    } else {
        cout << "Primes between " << interval_start << " and " << interval_end << ":" << endl;
        for (int prime_number : prime_numbers) {
            cout << prime_number << endl;
        }
    }

    return 0;
}
