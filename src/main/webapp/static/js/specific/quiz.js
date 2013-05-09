$(function () {
    "use strict";

    var questionMap = {}, currentQuestion = 1, quizChanged = false, questionChanged = false, quizForm = $('#quiz-form'),
        questionForm = $('#question-form');

    /**
     * Récupère une question avec son id et l'affiche
     * @param id identifiant de la question
     */
    function ajaxQuestion(id) {
        $.ajax({
            url: '/jtools/question/' + id
        })
            .done(function (response) {
                questionForm.find(':input').each(function () {
                    var name = $(this).attr('name'), jsonValue = response[name];
                    if (jsonValue !== undefined) {
                        if (this.type === 'radio') {
                            if ($(this).attr('value') === jsonValue) {
                                $(this).trigger('click');
                            }
                        } else {
                            $(this).val(jsonValue);
                        }
                    }
                });
            })
            .error(function () {
                jTools.alert.error('form');
            });
    }


    /**
     * Vide le formulaire des questions
     */
    function clearQuestionForm() {
        questionForm.find(':input').each(function () {
            if (this.type === 'radio') {
                if ($(this).attr('value') === '1') {
                    $(this).trigger('click');
                }
            } else {
                $(this).val('');
            }
        });
    }

    /**
     * Active le bouton
     * @param button objet jQuery du bouton
     */
    function enable(button) {
        button.removeAttr('disabled');
    }

    /**
     * Désactive le bouton
     * @param button objet jQuery du bouton
     */
    function disable(button) {
        button.attr('disabled', 'disabled');
    }

    /**
     * Gestion des modifications sur la navigation
     */
    function navChange() {
        if (Object.size(questionMap) >= currentQuestion) {
            enable($('#next-question'));
        } else {
            disable($('#next-question'));
        }
        if (currentQuestion > 1) {
            enable($('#previous-question'));
        } else {
            disable($('#previous-question'));
        }
    }

    function majNumber() {
        $('#nb').text(currentQuestion);
        $('#number').val(currentQuestion);
    }

    /**
     * Envoi le formulaire des questions
     * @param [quizMessage] message renvoyé précédemment par l'envoi du formulaire des quiz
     */
    function submitQuestion(quizMessage) {
        $('#question-form').ajaxSubmit({
            dataType: 'json',
            data: {
                quizId: $('#id-quiz').val()
            },
            success: function (questionResponse) {
                if (questionResponse.success) {
                    questionMap[currentQuestion] = questionResponse.data.id;
                    questionChanged = false;
                    $('#id-question').val(questionResponse.data.id);
                    $('#version-question').val(questionResponse.data.version);
                    if (quizMessage !== undefined) {
                        jTools.alert.success('form', quizMessage + " " + questionResponse.message);
                    } else {
                        jTools.alert.success('form', questionResponse.message);
                    }
                    navChange();
                } else {
                    jTools.alert.error('form', questionResponse.message);
                }
            },
            error: function () {
                jTools.alert.error('form');
            }
        });
    }

    /**
     * Sauvegarde les modifications effectuées sur le formulaire
     * @param quizChange true si le formulaire relatif au quiz a changé
     * @param questionChange true si le formulaire relatif aux questions a changé
     */
    function save(quizChange, questionChange) {
        var quizForm = $('#quiz-form'), questionForm = $('#question-form');
        if (quizChange && quizForm.valid() && (!questionChange || questionForm.valid())) {
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
        } else if (questionChange && questionForm.valid()) {
            submitQuestion();
        }
    }

    majNumber();

    //Gestion modification formulaire
    quizForm.find(':input').change(function () {
        quizChanged = true;
    });
    questionForm.find(':input').change(function () {
        questionChanged = true;
    });

    //Gestion click bouton ajouter
    $('#add-button').click(function () {
        save(quizChanged, questionChanged);
    });

    //Gestion navigation
    $('#previous-question').click(function () {
        currentQuestion -= 1;
        ajaxQuestion(questionMap[currentQuestion]);
        majNumber();
        navChange();
    });

    $('#next-question').click(function () {
        currentQuestion += 1;
        if (questionMap[currentQuestion] !== undefined) {
            ajaxQuestion(questionMap[currentQuestion]);
        } else {
            clearQuestionForm();
        }
        majNumber();
        navChange();
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
            }
        }
    });
});

