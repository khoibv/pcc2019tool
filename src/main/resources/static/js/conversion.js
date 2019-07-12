$(function() {
  $('.inputColumns').keypress(function(event) {
    var that = this;
    if(!$(that).val()) { return; }

    var keycode = (event.keyCode ? event.keyCode : event.which);
    	if(keycode === 13){
    		NE.conversion.addColumn(that);
    	}
  });

  $('.btn-conversion').click(function() {

    var selectColumns = [];
    $('#outParamList li').each(function(index, item) {
      selectColumns.push({
        columnName: $(item).text().trim(),
        comment: ''
      });
    });

    var whereColumns = [];
    $('#inParamList li').each(function(index, item) {
      whereColumns.push({
        columnName: $(item).text().trim(),
        comment: ''
      });
    });

    NE.sendRequest({
      data: JSON.stringify({
        selectColumns: selectColumns,
        whereColumns: whereColumns,
        serviceId: $('#serviceId').val(),
        author: $('#author').val(),
      }),
      url: NE.urls.convert,
      success: function(data) {
        alert('Success: ' + data);
      }
    });
  });
});

NE.conversion = {
  addColumn: function(control) {
    var target = '#' + $(control).data('target');
    $(target).append('<li>' + $(control).val() + '</li>');
    $(control).val('');
  }
}