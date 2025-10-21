let t1SubScore = 0;
let t1SuperScore = 0;
let t2SubScore = 0;
let t2SuperScore = 0;
let currentSegment = 0;

let progressSegments = document.getElementsByClassName("progress-segment");

function addPoint(index) {
  if (index == 1) {
    t1SubScore++;
    updateText();
    if (t1SubScore > 2) {
      t1SuperScore += 2;
      progressSegments[currentSegment].classList.add("team1");
      checkWin();
      resetSub();
    } else if (t1SubScore == 2 && t2SubScore == 2) {
      t1SuperScore += 1;
      t2SuperScore += 1;
      progressSegments[currentSegment].classList.add("draw");
      checkWin();
      resetSub();
    }
  } else {
    t2SubScore++;
    updateText();
    if (t2SubScore > 2) {
      t2SuperScore += 2;
      progressSegments[currentSegment].classList.add("team2");
      checkWin();
      resetSub();
    } else if (t2SubScore == 2 && t1SubScore == 2) {
      t2SuperScore += 1;
      t1SuperScore += 1;
      progressSegments[currentSegment].classList.add("draw");
      checkWin();
      resetSub();
    }
  }
}

function deletePoint(teamId) {
  if (teamId == 1 && t1SubScore > 0) {
    t1SubScore--;
  } else {
    if (t2SubScore > 0) {
      t2SubScore--;
    }
  }
  updateText();
}

function resetSub() {
  t1SubScore = 0;
  t2SubScore = 0;
  currentSegment++;
  updateText();
}

function updateText() {
  document.getElementById("t1SubScoreText").innerText = `${t1SubScore}`;
  document.getElementById("t2SubScoreText").innerText = `${t2SubScore}`;
  document.getElementById("superScoreCounter").innerText =
    `${t1SuperScore}-${t2SuperScore}`;
}

function checkWin() {
  if (t1SuperScore >= 12) {
    document.getElementById("game").innerHTML = `<h1>Team 1 Wins</h2>`;
    setTimeout(() => {
      t1SuperScore = 0;
      t2SuperScore = 0;
      resetSub();
      document.getElementById("game").innerHTML = `<h1>Eispunkt</h2>`;
    }, 5000);
  } else if (t2SuperScore >= 12) {
    document.getElementById("game").innerHTML = `<h1>Team 2 Wins</h2>`;
    setTimeout(() => {
      t1SuperScore = 0;
      t2SuperScore = 0;
      resetSub();
      document.getElementById("game").innerHTML = `<h1>Eispunkt</h2>`;
    }, 5000);
  } else {
    return;
  }
}
