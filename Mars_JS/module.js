const Module = (function () {
    const rbtn = document.getElementById("rbtn");
    const abtn = document.getElementById("abtn");
    let start = -4, end = 5;
    let speed = 1, position = 0;
    return {
        butClick: function () {
            if (event.target.id === "abtn") {
                document.getElementsByTagName("td").item(position - start).children[0].setAttribute("src", "");
                document.getElementsByTagName("td").item(position - start).children[1].textContent = position;
                position += speed;
                if (position > end || position < start) {
                    if (position % 10 === 5) {
                        end = position;
                        start = end - 9;
                    }
                    else {
                        if (position < 0) {
                            end = -Math.round(-position / 10) * 10 + 5;
                        }
                        else {
                            end = Math.round(position / 10) * 10 + 5;
                        }
                        start = end - 9;
                    }
                    let tds = document.getElementsByTagName("td");
                    for (let i = 0; i < tds.length; i++) {
                        tds.item(i).children[1].textContent = start + i;
                    }
                }
                speed *= 2;
                document.getElementsByName("speed").item(0).value = speed;
                document.getElementsByName("position").item(0).value = position;
                document.getElementsByTagName("td").item(position - start).children[0].setAttribute("src", "3.png");
                document.getElementsByTagName("td").item(position - start).children[0].setAttribute("width", "170px");
                document.getElementsByTagName("td").item(position - start).children[1].textContent = "";
            }
            else if (event.target.id === "rbtn") {
                speed = speed * (-1 / Math.abs(speed));
                document.getElementsByName("speed").item(0).value = speed;
            }
        },
        start: function () {
            abtn.addEventListener("click", Module.butClick);
            rbtn.addEventListener("click", Module.butClick);
        }
    };
}());