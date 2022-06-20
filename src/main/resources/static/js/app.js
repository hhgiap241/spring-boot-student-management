$(document).on("click", ".open-DeleteStudentDialog", function () {
    var studentId =$(this).attr('id');
    console.log(studentId);
    $("#deleteLink").attr("href", "/students/delete/" + studentId);
});