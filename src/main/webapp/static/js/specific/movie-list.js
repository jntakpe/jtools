function renderSeenBy(data, type, full) {
    "use strict";
    var icon = data === true ? 'icon-ok' : 'icon-remove';
    return "<button class='btn btn-mini btn-primary seen-by-btn' onclick='switchSeen($(this))' lineid=" + full.id + "><i class='" + icon + "'></i></button>";
}

function switchSeen(btn) {
    "use strict";
    var icon = btn.children('i'), changeTo, header, col;
    if (icon.hasClass('icon-ok')) {
        icon.removeClass('icon-ok');
        icon.addClass('icon-remove');
        changeTo = false;
    } else {
        icon.removeClass('icon-remove');
        icon.addClass('icon-ok');
        changeTo = true;
    }
    col = btn.parent('td');
    header = col.closest('table').find('th').eq(col.index());
    $.ajax({
        type: 'POST',
        url: 'movie/switch',
        data: {
            id: btn.attr('lineid'),
            field: header.text(),
            changeTo: changeTo
        }
    })
        .error(function () {
            jTools.alert.error('list');
        });
}


$(function () {
    "use strict";

    var movieTable, validForm, tableSelector;

    tableSelector = $('#movie-table');

    movieTable = tableSelector.dataTable({
        sAjaxSource: "movie/list",
        aoColumns: [
            {mData: "title", sWidth: 400},
            {mData: "director", sWidth: 200},
            {mData: "addedBy.login"},
            {mData: "addDate", sType: "date-euro-simple", mRender: function (data) {
                return moment(new Date(data)).format("DD/MM/YYYY");
            }},
            {mData: "ratings"},
            {mData: "ameliaSawIt", bSortable: false, sClass: "center", mRender: function (data, type, full) {
                return renderSeenBy(data, type, full);
            }},
            {mData: "selrakSawIt", bSortable: false, sClass: "center", mRender: function (data, type, full) {
                return renderSeenBy(data, type, full);
            }},
            {mData: "jujupiwiSawIt", bSortable: false, sClass: "center", mRender: function (data, type, full) {
                return renderSeenBy(data, type, full);
            }},
            {mData: "jossSawIt", bSortable: false, sClass: "center", mRender: function (data, type, full) {
                return renderSeenBy(data, type, full);
            }},
            jTools.table.actionPopup()
        ]
    });

    validForm = $('#popupForm').validate({
        submitHandler: function (form) {
            var row = $("#popup").data("row");
            $(form).ajaxSubmit({
                success: function (response) {
                    if (response.success) {
                        if (row) {
                            movieTable.fnDeleteRow(row);
                        }
                        movieTable.fnAddData(response.data);
                        jTools.alert.success('list', response.message);
                    } else {
                        jTools.alert.error('list', response.message);
                    }
                },
                error: function () {
                    jTools.alert.error('list');
                    $('#popup').modal('hide');
                }
            });

            $('#popup').modal('hide');
        },
        rules: {
            title: {
                required: true
            },
            director: {
                required: true
            }
        },
        messages: {
            title: {
                remote: "Ce film existe déjà."
            }
        }
    });

    $('#popup').on('shown', function () {
        $('#title').rules('add', {
            remote: {
                url: "movie/titleunicity",
                data: {
                    id: $('#id').val()
                }
            }
        });
    });

    $('#popup').on('hidden', function () {
        var form = $("#popupForm");
        validForm.resetForm();
        form.find('.control-group').removeClass('error');
        form.removeData('row');
        $('#title').removeData('previousData');
        form.find('input:hidden').val("");
        $('#jossSawIt').iCheck('uncheck');
        $('#selrakSawIt').iCheck('uncheck');
        $('#jujupiwiSawIt').iCheck('uncheck');
        $('#ameliaSawIt').iCheck('uncheck');
        $('#title').rules('remove', 'remote');
    });

    $('#seenBy input').iCheck({
        checkboxClass: 'icheckbox_square-orange',
        increaseArea: '50%'
    });
});

