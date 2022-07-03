
//章节测试评分模块
export function chapterScoring(choiceAnswers,gapfillingAnswers,judgeAnswers,programmeAnswers,choiceValues,gapfillingValues,judgeValues,programmeValues) {
    let score = 0;
    //对于选择题
    for (let i=0;i<choiceValues.length;i++){
        for (let j=0;j<choiceAnswers.length;j++){
            if (choiceValues[i].seq===choiceAnswers[j].seq){
                if (choiceValues[i].answer===undefined){
                    continue;
                }
                if (choiceValues[i].answer===choiceAnswers[j].answerRight){
                    score += 2;
                }
            }
        }

    }

    //对于填空题
    for (let i=0;i<gapfillingValues.length;i++){
        for (let j=0;j<gapfillingAnswers.length;j++){
            if (gapfillingValues[i].seq===gapfillingAnswers[j].seq){
                if (gapfillingValues[i].answer===undefined || gapfillingValues[i].answer==="<null>"){
                    continue;
                }
                if (gapfillingValues[i].answer===gapfillingAnswers[j].answerRight1
                    || gapfillingValues[i].answer===gapfillingAnswers[j].answerRight2
                    || gapfillingValues[i].answer===gapfillingAnswers[j].answerRight3
                    || gapfillingValues[i].answer===gapfillingAnswers[j].answerRight4){
                    score += 2;
                }
            }
        }

    }

    //对于判断题
    for (let i=0;i<judgeValues.length;i++){
        for (let j=0;j<judgeAnswers.length;j++){
            if (judgeValues[i].seq===judgeAnswers[j].seq){
                if (judgeValues[i].answer===undefined){
                    continue;
                }
                if (judgeValues[i].answer===judgeAnswers[j].answerRight){
                    score += 2;
                }
            }
        }

    }

    //对于编程题
    for (let i=0;i<programmeValues.length;i++){
        for (let j=0;j<programmeAnswers.length;j++){
            if (programmeValues[i].seq===programmeAnswers[j].seq){

                if (programmeValues[i].answer1===programmeAnswers[j].answerRight1){
                    if (programmeValues[i].answer1===undefined){
                        score += 0;
                    }
                    score += 4;
                }
                if (programmeValues[i].answer2===programmeAnswers[j].answerRight2){
                    if (programmeValues[i].answer2===undefined){
                        score += 0;
                    }
                    score +=4;
                }
                if (programmeValues[i].answer3===programmeAnswers[j].answerRight3){
                    if (programmeValues[i].answer3===undefined){
                        score += 0;
                    }
                    score +=4;
                }
            }
        }

    }
    return score;
}


//选择题库练习评分模块
export function bankChoiceScoring(choiceAnswers,choiceValues) {

    let score = 0;
    //10  1题10分
    for (let i=0;i<choiceValues.length;i++){
        for (let j=0;j<choiceAnswers.length;j++){
            if (choiceValues[i].seq===choiceAnswers[j].seq){
                if (choiceValues[i].answer===undefined){
                    continue;
                }
                if (choiceValues[i].answer===choiceAnswers[j].answerRight){
                    switch (choiceAnswers.length) {
                        case 10:
                            score += 10;
                            break;
                        case 25:
                            score += 4;
                            break;
                        case 50:
                            score += 2;
                            break;
                    }
                }
            }
        }

    }
    return score;
}
/*export function bankChoice_25Scoring(choiceAnswers,choiceValues) {

    let score = 0;
    //25  1题4分
    for (let i=0;i<choiceValues.length;i++){
        for (let j=0;j<choiceAnswers.length;j++){
            if (choiceValues[i].seq===choiceAnswers[j].seq){
                if (choiceValues[i].answer===undefined){
                    continue;
                }
                if (choiceValues[i].answer===choiceAnswers[j].answerRight){
                    score += 4;
                }
            }
        }

    }
    return score;
}*/

//填空题库练习评分模块
export function bankGapFillingScoring(gapfillingAnswers,gapfillingValues) {
    let score = 0;
    //10  1题10分
    for (let i = 0; i < gapfillingValues.length; i++) {
        for (let j = 0; j < gapfillingAnswers.length; j++) {
            if (gapfillingValues[i].seq === gapfillingAnswers[j].seq) {
                if (gapfillingValues[i].answer === undefined || gapfillingValues[i].answer === "<null>") {
                    continue;
                }
                if (gapfillingValues[i].answer === gapfillingAnswers[j].answerRight1
                    || gapfillingValues[i].answer === gapfillingAnswers[j].answerRight2
                    || gapfillingValues[i].answer === gapfillingAnswers[j].answerRight3
                    || gapfillingValues[i].answer === gapfillingAnswers[j].answerRight4) {
                    switch (gapfillingAnswers.length) {
                        case 10:
                            score += 10;
                            break;
                        case 25:
                            score += 4;
                            break;
                        case 50:
                            score += 2;
                            break;
                    }
                }
            }
        }

    }
    return score;
}

//判断题库练习评分模块
export function bankJudgeScoring(judgeAnswers,judgeValues) {
    let score = 0;
    //10  1题10分
    for (let i=0;i<judgeValues.length;i++){
        for (let j=0;j<judgeAnswers.length;j++){
            if (judgeValues[i].seq===judgeAnswers[j].seq){
                if (judgeValues[i].answer===undefined){
                    continue;
                }
                if (judgeValues[i].answer===judgeAnswers[j].answerRight){
                    switch (judgeAnswers.length) {
                        case 10:
                            score += 10;
                            break;
                        case 25:
                            score += 4;
                            break;
                        case 50:
                            score += 2;
                            break;
                    }
                }
            }
        }
    }
    return score;
}

//编程题库练习评分模块
export function bankProgrammeScoring(programmeAnswers,programmeValues) {
    let score = 0;
    //10  1题10分
    for (let i=0;i<programmeValues.length;i++){
        for (let j=0;j<programmeAnswers.length;j++){
            if (programmeValues[i].seq===programmeAnswers[j].seq){

                if (programmeValues[i].answer1===programmeAnswers[j].answerRight1){
                    if (programmeValues[i].answer1===undefined){
                        score += 0;
                    }
                    switch (judgeAnswers.length) {
                        case 10:
                            score += 10;
                            break;
                        case 25:
                            score += 4;
                            break;
                        case 50:
                            score += 2;
                            break;
                    }
                }
                if (programmeValues[i].answer2===programmeAnswers[j].answerRight2){
                    if (programmeValues[i].answer2===undefined){
                        score += 0;
                    }
                    switch (judgeAnswers.length) {
                        case 10:
                            score += 10;
                            break;
                        case 25:
                            score += 4;
                            break;
                        case 50:
                            score += 2;
                            break;
                    }
                }
                if (programmeValues[i].answer3===programmeAnswers[j].answerRight3){
                    if (programmeValues[i].answer3===undefined){
                        score += 0;
                    }
                    switch (judgeAnswers.length) {
                        case 10:
                            score += 10;
                            break;
                        case 25:
                            score += 4;
                            break;
                        case 50:
                            score += 2;
                            break;
                    }
                }
            }
        }
    }
    return score;
}

//c评分模块
export function cScoring(choiceAnswers,gapfillingAnswers,programmeAnswers,choiceValues,gapfillingValues,programmeValues) {
    let score = 0;
    //对于选择题
    for (let i=0;i<choiceValues.length;i++){
        for (let j=0;j<choiceAnswers.length;j++){
            if (choiceValues[i].seq===choiceAnswers[j].seq){
                if (choiceValues[i].answer===undefined){
                    continue;
                }
                if (choiceValues[i].answer===choiceAnswers[j].answerRight){
                    if (choiceAnswers[j].seq < 10){
                        score += 2;
                    }else if(choiceAnswers[j].seq < 50){
                        score += 1;
                    }

                }
            }
        }

    }

    //对于填空题
    for (let i=0;i<gapfillingValues.length;i++){
        for (let j=0;j<gapfillingAnswers.length;j++){
            if (gapfillingValues[i].seq===gapfillingAnswers[j].seq){
                if (gapfillingValues[i].answer===undefined || gapfillingValues[i].answer==="<null>"){
                    continue;
                }
                if (gapfillingValues[i].answer===gapfillingAnswers[j].answerRight1
                    || gapfillingValues[i].answer===gapfillingAnswers[j].answerRight2
                    || gapfillingValues[i].answer===gapfillingAnswers[j].answerRight3
                    || gapfillingValues[i].answer===gapfillingAnswers[j].answerRight4){
                    score += 2;
                }
            }
        }

    }

    //对于编程题
    for (let i=0;i<programmeValues.length;i++){
        for (let j=0;j<programmeAnswers.length;j++){
            if (programmeValues[i].seq===programmeAnswers[j].seq){

                if (programmeValues[i].answer1===programmeAnswers[j].answerRight1){
                    if (programmeValues[i].answer1===undefined){
                        score += 0;
                    }
                    score += 5;
                }
                if (programmeValues[i].answer2===programmeAnswers[j].answerRight2){
                    if (programmeValues[i].answer2===undefined){
                        score += 0;
                    }
                    score +=5;
                }
                if (programmeValues[i].answer3===programmeAnswers[j].answerRight3){
                    if (programmeValues[i].answer3===undefined){
                        score += 0;
                    }
                    score +=5;
                }
            }
        }

    }
    return score;
}