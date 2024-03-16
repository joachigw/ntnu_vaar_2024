window.onload = function () {
    let incrCountBtn = document.getElementById("incr_count_btn");
    let resetCountBtn = document.getElementById("reset_count_btn");
    let imgBtn = document.getElementById("img_btn");
    let arrayBtn = document.getElementById("array_btn");
    let clearArrayBtn = document.getElementById("clear_array_btn");
    let counter = document.getElementById("counter");
    let img = document.getElementById("img");
    let array = document.getElementById("array");
    let arrayList = document.getElementById("array_list");
    let wordArrayIndex = 0;
    const wordArray = [
        "bicycle",
        "car",
        "house",
        "snake",
        "array",
        "button",
        "sofa",
        "kitchen",
        "table",
        "seal",
        "glass",
        "dinner",
    ];

    incrCountBtn.onclick = function () {
        counter.textContent = parseInt(counter.textContent) + 1;
    };

    resetCountBtn.onclick = function () {
        counter.textContent = 0;
    };

    imgBtn.onclick = function () {
        if (img.style.visibility == "hidden") {
            img.style.visibility = "visible";
        } else {
            img.style.visibility = "hidden";
        }
    };

    arrayBtn.onclick = function () {
        let newWord = wordArray[wordArrayIndex];
        let newListElement = document.createElement("li");

        newListElement.textContent = newWord;
        newListElement.style.listStyleType = "none";
        newListElement.style.display = "inline-block";

        if (wordArrayIndex == 0) {
            array.style.display = "none";
            arrayList.appendChild(newListElement);
        } else if (wordArrayIndex >= 1 && wordArrayIndex < wordArray.length) {
            arrayList.appendChild(document.createTextNode(", "));
            arrayList.appendChild(newListElement);
        } else {
            alert("No more words available!");
        }

        wordArrayIndex++;
    };

    clearArrayBtn.onclick = function () {
        array.style.display = "block";

        while (arrayList.firstChild) {
            arrayList.textContent = "";
        }

        wordArrayIndex = 0;
    };
};
