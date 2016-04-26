function enableUserCheckBoxListener() {

    $(':checkbox').change(function () {
        var enabled = $(this).is(':checked');
        var id = $(this).closest('tr').attr('id');
        $.ajax({
            type: "PATCH",
            url: ajaxUrl + "/" + id,
            headers: {
                'Content-Type' : 'application/json'
            },
            data: JSON.stringify({
                enabled: enabled
            }),
            success: function () {
                updateTable();
                successNoty(enabled ? 'Enabled' : 'Disabled');
            }
        });
    });
}