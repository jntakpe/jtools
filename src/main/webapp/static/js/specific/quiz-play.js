$(function () {
    'use strict';

    var countdownDuration = $('#question-duration').val() || 30;

    //Radio
    $('input').iCheck({
        radioClass: 'iradio_square-orange',
        increaseArea: '50%'
    });

    //Timer
    $('#timer').pietimer({
        timerSeconds: countdownDuration,
        color: '#e36b23',
        fill: false,
        showPercentage: true,
        callback: function () {
            $('#valid-question').trigger('click');
        }
    });

    //Gestion progress bar
    $('#quiz-progress').css('width', function () {
        var current = $('#question-number').text(), total = $('#question-total').val();
        return ((current / total) * 100).toString() + "%";
    });

    $('#alert-explanation').alert();

    //Click validation question
    $('#valid-question').click(function () {
        var answer, quizId, questionId;
        quizId = $('#quiz-id').val();
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
                alert(response.explanation);
            }).error(function () {
                jTools.alert.error('form');
            });
    });
});

