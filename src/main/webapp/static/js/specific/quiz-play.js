/**
 * Initialisation du timer
 * @param duration duréé d'un cycle
 */
function initTimer(duration) {
    "use strict";
    $('#timer').pietimer({
        timerSeconds: duration,
        color: '#e36b23',
        fill: false,
        showPercentage: true,
        callback: function () {
            $('#valid-question').trigger('click');
        }
    });
}
/**
 * Affichage de l'explication en cas de bonne réponse
 * @param explanation explication

 */
function displaySuccess(explanation) {
    "use strict";
    var alertDiv = $("#alert-explanation"), alertIcon = $('#explanation-icon'),
        alertMessage = $('#explanation-message');
    clearTimeout(jTools.alert.currentTimeout);
    alertMessage.text('BONNE RÉPONSE. ' + explanation);
    if (alertDiv.hasClass('alert-error')) {
        alertDiv.removeClass('alert-error');
    }
    if (!alertDiv.hasClass('alert-success')) {
        alertDiv.addClass('alert-success');
    }
    if (alertIcon.hasClass('icon-warning-sign')) {
        alertIcon.removeClass('icon-warning-sign');
    }
    if (!alertIcon.hasClass('icon-ok')) {
        alertIcon.addClass('icon-ok');
    }
    alertDiv.addClass('in');
}

/**
 * Affiche de l'explication en cas de mauvaise réponse
 * @param explanation explication
 * @param correctAnswer index de la bonne réponse
 */
function displayError(explanation, correctAnswer) {
    "use strict";
    var alertDiv = $("#alert-explanation"), alertIcon = $('#explanation-icon'),
        alertMessage = $('#explanation-message');
    alertMessage.text('MAUVAISE RÉPONSE. ' + explanation);
    if (alertDiv.hasClass('alert-success')) {
        alertDiv.removeClass('alert-success');
    }
    if (!alertDiv.hasClass('alert-error')) {
        alertDiv.addClass('alert-error');
    }
    if (alertIcon.hasClass('icon-ok')) {
        alertIcon.removeClass('icon-ok');
    }
    if (!alertIcon.hasClass('icon-warning-sign')) {
        alertIcon.addClass('icon-warning-sign');
    }
    alertDiv.addClass('in');
    $('#quiz-core').find('li').find('input[name="quiz-radio"][value="' + correctAnswer + '"]').next().trigger('click');
}

/**
 * Update de la progress bar
 */
function updateProgress() {
    "use strict";
    //Gestion progress bar
    $('#quiz-progress').css('width', function () {
        var current = $('#question-number').text(), total = $('#question-total').val();
        return ((current / total) * 100).toString() + "%";
    });
}

/**
 * Affichage de la question suivante
 * @param event événement
 */
function nextQuestion(event) {
    "use strict";
    var question = event.data.question;
    if (question === null) {
        window.location.pathname = "/jtools/quiz/result/" + $('#quiz-id').val();
    } else {
        $('#question-id').val(question.id);
        $('#question-number').text(question.number + 1);
        $('#question-label').text(question.label);
        $('#firstAnswer').text(question.firstAnswer);
        $('#secondAnswer').text(question.secondAnswer);
        if (question.thirdAnswer) {
            $('#thirdAnswer').text(question.thirdAnswer);
            if ($('#third-li').not(':visible')) {
                $('#third-li').show();
            }
        } else {
            $('#third-li').hide();
        }
        if (question.fourthAnswer) {
            $('#fourthAnswer').text(question.fourthAnswer);
            if ($('#fourth-li').not(':visible')) {
                $('#fourth-li').show();
            }
        } else {
            $('#fourth-li').hide();
        }
        $('#firstAnswer').trigger('click');
        initTimer(question.duration);
        updateProgress();
        $('#timer').pietimer('start');
        $('#valid-question').show();
        $('#alert-explanation').removeClass('in');
    }
}


$(function () {
    'use strict';

    //Cache les radios inutiles
    if (!$('#fourthAnswer').text()) {
        $('#fourth-li').hide();
    }
    if (!$('#thirdAnswer').text()) {
        $('#third-li').hide();
    }

    //Radio
    $('input').iCheck({
        radioClass: 'iradio_square-orange',
        increaseArea: '50%'
    });

    //Timer
    initTimer($('#question-duration').val() || 30);

    //Progress bar
    updateProgress();

    //Click validation question
    $('#valid-question').click(function () {
        $('#timer').pietimer('reset');
        $(this).hide();
        var answer, questionId;
        $('#quiz-id').val();
        questionId = $('#question-id').val();
        answer = $('#quiz-core').find('input[type="radio"]:checked').val();
        $.ajax({
            url: '/jtools/question/' + questionId,
            type: 'POST',
            data: {
                answer: answer
            }
        })
            .done(function (response) {

                if (response.goodAnswer) {
                    displaySuccess(response.explanation, response.correctAnswer);
                } else {
                    displayError(response.explanation, response.correctAnswer);
                }
                $('#next-explanation').unbind('click').bind('click', {question: response.question}, nextQuestion);
            }).error(function () {
                jTools.alert.error('form');
            });
    });
});



