var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;
var $filterForm = $('#filter');

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: function (data) {
            updateTableByData(data);
        }
    });
    return false;
}

$(function () {
    datatableApi = $('#datatable').DataTable(
        {
            "ajax": {
                "url": ajaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type == 'display') {
                            var dateTimeString = new Date(date).toISOString();
                            return dateTimeString.substring(0, 10) + ' ' + dateTimeString.substring(11, 16);
                        }
                        return date;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).addClass(data.exceed ? "exceeded" : "normal");
            },
            "initComplete": makeEditable
        });

    $('#filter').submit(function () {
        updateTable();
        return false;
    });

    $('#dateTime').datetimepicker({
        format: "Y-m-d\\TH:i"
    });
    $filterForm.find('input[name$=Date]').datetimepicker({
        timepicker: false,
        format: "Y-m-d"
    });
    $filterForm.find('input[name$=Time]').datetimepicker({
        datepicker: false,
        format: "H:i"
    });
});

