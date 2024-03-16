#include <chrono>
#include <algorithm>
#include <iostream>
#include <functional>
#include <list>
#include <thread>
#include <mutex>
#include <vector>
#include <condition_variable>

using namespace std;

class Workers {
private:
    int n_workers;
    vector<thread> threads;
    list<function<void()>> tasks;
    bool stop_working;
    mutex tasks_mutex;
    condition_variable tasks_cv;

public:
    explicit Workers(int workers) {
        this->n_workers = workers;
        this->stop_working = false;
    }

    void start() {
        for (int i = 0; i < n_workers; ++i) {
            this->threads.emplace_back([this] () {
                while (true) {
                    function<void()> task;
                    {
                        unique_lock<mutex> lock(tasks_mutex);
                        tasks_cv.wait(lock, [this] {
                            return !tasks.empty() || stop_working;
                        });

                        if (stop_working && (tasks.empty())) break;

                        task = *tasks.begin();
                        tasks.pop_front();
                    }
                    task();
                }
                cout << "Thread no. " << this_thread::get_id() << " finished!" << endl;
            });
        }
    }

    void stop() {
        this->stop_working = true;
        tasks_cv.notify_all();
    }

    void post(void (*function_ptr)()) {
        if (stop_working) return;

        {
            unique_lock<mutex> lock(tasks_mutex);
            tasks.emplace_back(function_ptr);
            tasks_cv.notify_one();
        }
    }

    void post_timeout(void (*function_ptr)(), int timeout_duration) {
        if (stop_working) return;
        {
        unique_lock<mutex> lock(tasks_mutex);
        tasks.emplace_back([function_ptr, timeout_duration] {
            auto start = chrono::high_resolution_clock::now();
            this_thread::sleep_for(chrono::milliseconds(timeout_duration));
            function_ptr();
            auto end = chrono::high_resolution_clock::now();
            auto duration = chrono::duration_cast<chrono::seconds>(end - start);
            cout << "time measured in timeout task: " << duration.count() << "s" << endl;
        });
        }
        tasks_cv.notify_one();
    }

    void join() {
        for (auto &thread : threads) {
            thread.join();
        }
    }
};