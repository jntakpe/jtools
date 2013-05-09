var questionMap = {}, currentQuestion = 1, quizChanged = false, questionChanged = false;
/**
 * Active le bouton
 * @param button objet jQuery du bouton
 */
var enable = function (button) {
    "use strict";
    button.removeAttr('disabled');
};

/**
 * Désactive le bouton
 * @param button objet jQuery du bouton
 */
var disable = function (button) {
    "use strict";
    button.attr('disabled', 'disabled');
};

/**
 * Envoi le formulaire des questions
 * @param [quizMessage] message renvoyé précédemment par l'envoi du formulaire des quiz
 */
var submitQuestion = function (quizMessage) {
    "use strict";
    $('#question-form').ajaxSubmit({
        dataType: 'json',
        data: {
            quizId: $('#id-quiz').val()
        },
        success: function (questionResponse) {
            if (questionResponse.success) {
                questionMap[1] = questionResponse.data.id;
                $('#id-question').val(questionResponse.data.id);
                $('#version-question').val(questionResponse.data.version);
                questionChanged = false;
                if (quizMessage) {
                    jTools.alert.success('form', quizMessage + " " + questionResponse.message);
                } else {
                    jTools.alert.success('form', questionResponse.message);
                }
            } else {
                jTools.alert.error('form', questionResponse.message);
            }
        },
        error: function () {
            jTools.alert.error('form');
        }
    });
};
/**
 * Sauvegarde les modifications effectuées sur le formulaire
 * @param quizChange true si le formulaire relatif au quiz a changé
 * @param questionChange true si le formulaire relatif aux questions a changé
 */
var save = function (quizChange, questionChange) {
    "use strict";
    var quizForm = $('#quiz-form');
    if (quizChange && quizForm.valid() && (!questionChange || $('#question-form').valid())) {
        quizForm.ajaxSubmit({
            dataType: 'json',
            success: function (quizResponse) {
                if (quizResponse.success) {
                    quizChanged = false;
                    $('#id-quiz').val(quizResponse.data.id);
                    $('#version-quiz').val(quizResponse.data.version);
                    if (questionChange) {
                        submitQuestion(quizResponse.message);
                    } else {
                        jTools.alert.success('form', quizResponse.message);
                    }
                } else {
                    jTools.alert.error('form', quizResponse.message);
                }
            },
            error: function () {
                jTools.alert.error('form');
            }
        });
    } else if (questionChange && $('#question-form').valid()) {
        submitQuestion();
    }
};

$(function () {
    "use strict";
    var quizForm = $('#quiz-form'), questionForm = $('#question-form');

    //Gestion numéro question actuelle
    $('#legend-question').append(currentQuestion);
    $('#number').val(currentQuestion);

    //Gestion click bouton ajouter
    $('#add-button').click(function () {
        save(quizChanged, questionChanged);
    });

    //Gestion modification formulaire
    quizForm.find(':input').change(function () {
        quizChanged = true;
    });
    questionForm.find(':input').change(function () {
        questionChanged = true;
    });

    //Validation
    quizForm.validate({
        rules: {
            title: {
                required: true,
                maxlength: 50,
                remote: {
                    url: '/jtools/quiz/titlecontrol',
                    data: {
                        id: $('#id-quiz').val()
                    }
                }
            }
        },
        messages: {
            title: {
                remote: "Ce titre est déjà utilisé"
            }
        }
    });
    questionForm.validate({
        rules: {
            label: {
                required: true
            },
            firstAnswer: {
                required: true
            },
            secondAnswer: {
                required: true
            },
            explanation: {
                required: true
            }
        }
    });
});

