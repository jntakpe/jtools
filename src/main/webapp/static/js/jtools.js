/**
 * Scope de l'application jTools
 * @type {{ajax: {control: Function, remove: Function}, alert: {defaultTimeout: number, currentTimeout: number, success: Function, error: Function}, table: {action: Function}, popup: {confirm: Function}}}
 */
var jTools = {
    ajax: {
        /**
         * Contrôle ajax
         * @param url du contrôleur gérant ce champ
         * @returns {{url: *, data: {id: (*|jQuery)}}}
         */
        control: function (url) {
            'use strict';
            return {
                url: url,
                data: {
                    id: $('#id').val()
                }
            };
        },
        /**
         * Suppression d'une ligne
         * @param params
         */
        remove: function (params) {
            "use strict";
            var dataTable = $('table[id$=-table]').dataTable(), path = window.location.pathname, deleteUrl;
            $('#confirmDeletePopup').modal('hide');
            deleteUrl = path.match(/\/$/) ? path + params.data.id : path + "/" + params.data.id;
            $.ajax({
                type: 'DELETE',
                url: deleteUrl
            }).done(
                function (data) {
                    if (data.success) {
                        dataTable.fnDeleteRow(params.data.row);
                        jTools.alert.success("list", data.message);
                    } else {
                        dataTable.fnReloadAjax();
                        jTools.alert.error("list", data.message);
                    }
                }
            ).error(
                function () {
                    dataTable.fnReloadAjax();
                    jTools.alert.error("list", "Une erreur inconnue est survenue");
                }
            );
        }
    },
    alert: {
        //Timeout par défaut
        defaultTimeout: 10000,
        //Timeout courant
        currentTimeout: 0,
        /**
         * Affiche un message de succès dans le bandeau
         * @param suffix du conteneur de l'alerte ('form' pour alert-form ou 'list' pour alert-list)
         * @param [message] à afficher
         */
        success: function (suffix, message) {
            "use strict";
            var alertDiv = $("#alert-" + suffix), alertIcon = alertDiv.children('i');
            if (!message) {
                message = "Opération effectuée avec succès";
            }
            clearTimeout(jTools.alert.currentTimeout);
            alertDiv.children('span').text(message);
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
            jTools.alert.currentTimeout = window.setTimeout(function () {
                alertDiv.removeClass('in');
            }, jTools.alert.defaultTimeout);
        },
        /**
         * Affiche un message d'erreur dans le bandeau
         * @param suffix conteneur de l'alerte
         * @param [message] à afficher
         */
        error: function (suffix, message) {
            "use strict";
            var alertDiv = $("#alert-" + suffix), alertIcon = alertDiv.children('i');
            if (!message) {
                message = "Une erreur inconnue est survenue";
            }
            clearTimeout(jTools.alert.currentTimeout);
            alertDiv.children('span').text(message);
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
        }
    },
    table: {
        action: function () {
            "use strict";
            return {
                mData: "id",
                sWidth: 25,
                bSearchable: false,
                bSortable: false,
                sClass: "center",
                mRender: function (data) {
                    var path = window.location.pathname, editUrl, btnEdit, fct, btnDelete;
                    editUrl = path.match(/\/$/) ? path + data : path + "/" + data;
                    btnEdit = "<a href='" + editUrl + "'><i class='icon-edit icon-large'></i></a>";
                    fct = "jTools.popup.confirm(" + data + ",$(this))";
                    btnDelete = "<a href='javascript:;' onclick='" + fct + "'>" +
                        "<i class='icon-trash icon-large pull-right'></i></a>";
                    return btnEdit + btnDelete;
                }
            };
        },
        button: function (icon, text) {
            "use strict";
            return {
                mData: "id",
                sWidth: 80,
                bSearchable: false,
                bSortable: false,
                sClass: "center",
                mRender: function (data) {
                    var link = window.location.pathname + "/" + data;
                    return '<a class="btn btn-primary" href="' + link + '"><i class="' + icon + '"></i> ' + text + '</a>';
                }
            };
        }
    },
    popup: {
        /**
         * Affiche la popup de confirmation de la suppression d'une ligne
         * @param id identifiant de la ligne
         * @param event évennement
         */
        confirm: function (id, event) {
            "use strict";
            $('#delete-btn').off('click', jTools.ajax.remove)
                .on('click', {id: id, row: event.parents('tr')[0]}, jTools.ajax.remove);
            $('#confirmDeletePopup').modal();
        }
    }
};

/**
 * jQuery document ready
 */
$(function () {
    'use strict';

    //Fermeture automatique des alertes
    if ($('#alert-form, #alert-list').is(':visible') && $('#alert-form, #alert-list').hasClass('success')) {
        setTimeout(function () {
            $('#alert-form, #alert-list').removeClass('in');
            clearTimeout(jTools.alert.currentTimeout);
        }, jTools.alert.defaultTimeout);
    }

    //Fermeture des alertes après click sur 'close'
    $('#close-alert-form, #close-alert-list').click(function () {
        $(this).parent('.alert').removeClass('in');
    });

    //Tooltips
    $('a[data-toggle=tooltip]').tooltip();
});

/**
 * Prototypage
 */
Object.size = function (obj) {
    "use strict";
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) {
            size += 1;
        }
    }
    return size;
};
