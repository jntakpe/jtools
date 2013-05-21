$(function () {
    "use strict";

    var questions = [], currentQuestion = 0, quizChanged = false, questionChanged = false, quizForm = $('#quiz-form'),
        questionForm = $('#question-form'), idQuiz = $('#id-quiz');

    /**                                                                                                                  jtools
     * Récupère une question avec son id et l'affiche
     * @param id identifiant de la question
     */
    function ajaxQuestion(id) {
        $.ajax('/jtools/question/' + id)
            .done(function (response) {
                questionForm.find(':input').each(function () {
                    var name = $(this).attr('name'), jsonValue = response[name];
                    if (jsonValue !== undefined) {
                        if (this.type === 'radio') {
                            if (parseInt($(this).attr('value'), 10) === jsonValue) {
                                $(this).trigger('click');
                            }
                        } else {
                            $(this).val(jsonValue);
                            if ($(this).attr('name') === 'thirdAnswer') {
                                if ($.trim($(this).val()) !== '') {
                                    $("#third-radio").removeClass('hide');
                                } else {
                                    $("#third-radio").addClass('hide');
                                }
                            } else if ($(this).attr('name') === 'fourthAnswer') {
                                if ($.trim($(this).val()) !== '') {
                                    $("#fourth-radio").removeClass('hide');
                                } else {
                                    $("#fourth-radio").addClass('hide');
                                }
                            }
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
            if (!$("#third-radio").hasClass('hide')) {
                $("#third-radio").addClass('hide');
            }
            if (!$("#fourth-radio").hasClass('hide')) {
                $("#fourth-radio").addClass('hide');
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
        if (questions.length > currentQuestion) {
            enable($('#next-question'));
            enable($('#delete-question'));
        } else {
            disable($('#next-question'));
            disable($('#delete-question'));
        }
        if (currentQuestion > 0) {
            enable($('#previous-question'));
        } else {
            disable($('#previous-question'));
        }
    }

    /**
     * Met à jour le numéro de la question actuelle
     */
    function majNumber() {
        $('#nb').text(currentQuestion + 1);
        $('#number').val(currentQuestion);
    }

    /**
     * Dans le cas d'une mise à jour de quiz récupère la liste des ids de questions
     */
    function getQuestions() {
        $.ajax('/jtools/question/' + $('#id-quiz').val() + '/list')
            .done(function (response) {
                questions = response;
                ajaxQuestion(questions[0]);
                navChange();
            })
            .error(function () {
                jTools.alert.error('form');
            });
    }

    /**
     * Supprime une question en fonction de son identifiant
     * @param id identifiant de la question a supprimer
     */
    function ajaxDelete(id) {
        $.ajax({
            url: '/jtools/question/' + id,
            type: 'DELETE'
        })
            .done(function (response) {
                if (response.success) {
                    questions.splice(currentQuestion, 1);
                    jTools.alert.success('form', response.message);
                    if (currentQuestion > 0) {
                        $('#previous-question').trigger('click');
                    } else {
                        clearQuestionForm();
                        navChange();
                        majNumber();
                    }
                } else {
                    jTools.alert.error('form', response.message);
                }
            })
            .error(function () {
                jTools.alert.error('form');
            });
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
                    questions.push(questionResponse.data.id);
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

    if (idQuiz.val() && questions.length === 0) {
        getQuestions();
    }

    //Gestion modification formulaire
    quizForm.find(':input').change(function () {
        quizChanged = true;
    });
    questionForm.find(':input').change(function () {
        questionChanged = true;
        if ($(this).attr('name') === 'thirdAnswer') {
            if ($.trim($(this).val()) !== '') {
                $("#third-radio").removeClass('hide');
            } else {
                $("#third-radio").addClass('hide');
            }
        } else if ($(this).attr('name') === 'fourthAnswer') {
            if ($.trim($(this).val()) !== '') {
                $("#fourth-radio").removeClass('hide');
            } else {
                $("#fourth-radio").addClass('hide');
            }
        }
    });

    //Gestion click bouton ajouter
    $('#add-button').click(function () {
        save(quizChanged, questionChanged);
    });

    //Gestion click bouton supprimer
    $('#delete-question').click(function () {
        ajaxDelete($('#id-question').val());
    });

    //Gestion navigation
    $('#previous-question').click(function () {
        currentQuestion -= 1;
        ajaxQuestion(questions[currentQuestion]);
        majNumber();
        navChange();
    });

    $('#next-question').click(function () {
        currentQuestion += 1;
        if (questions[currentQuestion] !== undefined) {
            ajaxQuestion(questions[currentQuestion]);
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
                        id: idQuiz.val()
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
            duration: {
                required: true,
                digits: true
            }
        }
    });
});

