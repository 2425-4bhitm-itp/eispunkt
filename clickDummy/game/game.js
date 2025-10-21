let t1SubScore = 0;
let t1SuperScore = 0;
let t2SubScore = 0;
let t2SuperScore = 0;
let currentSegment = 0;

console.log("(POST) /api/stage/{stage} - Stage Objekt  im request body");
console.log(`insert into Stage (stagenumber, game_gameid)
                 values ({stage.stagenumber}, {stage.gameId});`);
console.log(`(POST) \`/api/turn/{turn}\` - Turn Objekt mit im request body`);
console.log(`insert into Turn (stage_stageid) values ({turn.stage_stageid});`);
console.log("(POST) /api/score/{score} - Score Objekt im request body");
console.log(`insert into Score (score, team_teamid, turn_turnid) values ({score.score},
            {score.turn_turnid}, {score.game_gameId});`);

console.log("(GET) `/api/game/{gameId}/summary`");
console.log(`select t.teamid, t.name, s.stageid, tu.turnid, sc.scoreid, sc.score
             from GAME g
                      join public.game_team gt on g.gameid = gt.games_gameid
                      join public.team t on t.teamid = gt.teams_teamid
                      join public.stage s on g.gameid = s.game_gameid
                      join turn tu on s.stageid = tu.stage_stageid
                      join score sc on tu.turnid = sc.turn_turnid
             where g.gameid = 2
             group by t.teamid, s.stageid, tu.turnid, sc.scoreid;`);

let progressSegments = document.getElementsByClassName("progress-segment");

function addPoint(index) {
  console.log(
    "(PUT) `/api/scores/{score}` - Score Objekt mit neuem Punktestand im request body",
  );
  console.log(`UPDATE public.score
                 SET score       = score.score,
                     team_teamid = score.team,
                     turn_turnid = score.turn
                 WHERE scoreid = score.scoreId;`);

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
  console.log(
    "(PUT) /api/scores/{score} - Score Objekt mit neuem Punktestand im request body",
  );
  console.log(`UPDATE public.score
                 SET score       = score.score,
                     team_teamid = score.team,
                     turn_turnid = score.turn
                 WHERE scoreid = score.scoreId;`);

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
  console.log("(POST) /api/stage/{stage} - Stage Objekt  im request body");
  console.log(`insert into Stage (stagenumber, game_gameid)
                 values ({stage.stagenumber}, {stage.gameId});`);
  console.log(`(POST) \`/api/turn/{turn}\` - Turn Objekt mit im request body`);
  console.log(
    `insert into Turn (stage_stageid) values ({turn.stage_stageid});`,
  );
  console.log("(POST) /api/score/{score} - Score Objekt im request body");
  console.log(`insert into Score (score, team_teamid, turn_turnid) values ({score.score},
            {score.turn_turnid}, {score.game_gameId});`);

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
    document.getElementById("winnerText").innerHTML = `<h2>Eispunkt Wins</h2>`;
    openModal();
  } else if (t2SuperScore >= 12) {
    document.getElementById("winnerText").innerHTML = `<h2>Eisbär Wins</h2>`;
    openModal();
  } else {
    return;
  }
}

function resetGame() {
  resetSub();
  currentSegment = 0;
  t1SuperScore = 0;
  t2SuperScore = 0;
  for (let i = 0; i < progressSegments.length; i++) {
    progressSegments[i].classList.remove("team1", "team2", "draw");
  }
  closeModal();
  updateText();
}

function openModal() {
  document.getElementById("overlay").classList.add("active");
}

function closeModal() {
  document.getElementById("overlay").classList.remove("active");
}
