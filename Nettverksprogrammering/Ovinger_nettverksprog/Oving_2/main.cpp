#include "Workers.hpp"
#include <iostream>

using namespace std;

mutex print_mutex;

void taskA() {
    print_mutex.lock();
    cout << "hello from taskA!" << endl;
    print_mutex.unlock();
};

void taskB() {
    print_mutex.lock();
    cout << "hello from taskB!" << endl;
    print_mutex.unlock();
};

void taskC() {
    print_mutex.lock();
    cout << "hello from taskC!" << endl;
    print_mutex.unlock();
};

void taskD() {
    print_mutex.lock();
    cout << "hello from taskD!" << endl;
    print_mutex.unlock();
};

void taskE() {
    print_mutex.lock();
    cout << "hello from taskE!" << endl;
    print_mutex.unlock();
};


int main() {

    // Initialize worker threads and the event loop
    Workers worker_threads(4);
    Workers event_loop(1);

    // Start the thread pools
    cout << "Starting worker_threads and event_loop..." << endl;
    worker_threads.start();
    event_loop.start();

    // Add tasks to the thread pool 'worker_threads'9
    cout << "Posting tasks for worker_threads..." << endl;
    worker_threads.post(taskA);
    worker_threads.post(taskB);
    worker_threads.post(taskC);
    worker_threads.post(taskD);

    cout << "Starting task no. 5..." << endl;
    worker_threads.post(taskE);

    // Timeout test
    cout << "Testing post_timeout..." <<  endl;
    worker_threads.post_timeout(taskA, 3000);
    cout << "post_timeout done." << endl;

    // Test thread pool with only one thread (C should be completed before D)
    cout << "Posting tasks for event_loop..." << endl;
    event_loop.post(taskC);
    event_loop.post(taskD);


    worker_threads.stop();
    event_loop.stop();

    cout << "Joining threads..." << endl;
    worker_threads.join();
    event_loop.join();

    cout << "DONE" << endl;

    return 0;
}

