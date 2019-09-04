$(document).ready(function () {
    //refresh button
    $("#refresh").on("click", function () {
        refreshTable();
    });
    //form submit handler
    $("#animal-rest-form").submit(function (e) {
        e.preventDefault();
        saveAnimal();
    });
    //when site is ready, query for data
    refreshTable();
});

function refreshTable() {
    $("#tbody").html("");
    $.get({
        url: "/rest/as/animal/all",
        success: function (data) {
            //called when successful
            data.forEach(function (row) {
                $('#tbody').append("<tr>" +
                    "<td>" + row.id + "</td>" +
                    "<td>" + row.breed + "</td>" +
                    "<td>" + row.weight + "</td>" +
                    "<td>" + row.age + "</td>" +
                    "<td>" + row.name + "</td>" +
                    "<td><a href='/rest/as/animal/" + row.id + "'>Link</td>" +
                    "</tr>");
            });
        },
    })
}

function saveAnimal() {
    let form = $("#animal-rest-form");
    let breed = $("#breed").val();
    let weight = $("#weight").val();
    let age = $("#age").val();
    let name = $("#name").val();

    $.post({
        url: "/rest/as/animal/add",
        data: JSON.stringify({id: null, breed: breed, weight: weight, age: age, name: name}),
        dataType: 'json',
        contentType: 'application/json',
        success: function (row) {
            $('#tbody').append("<tr>" +
                "<td>" + row.id + "</td>" +
                "<td>" + row.breed + "</td>" +
                "<td>" + row.weight + "</td>" +
                "<td>" + row.age + "</td>" +
                "<td>" + row.name + "</td>" +
                "<td><a href='/rest/as/animal/" + row.id + "'>Link</td>" +
                "</tr>");
            $("input[type=text]").val("");
        }
    })
}