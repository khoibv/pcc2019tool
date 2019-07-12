$(function() {
  // Set giá trị khởi tạo màn hình
  var author = NE.Storage.get('author');
  if(author) {
    $('#author').val(author);
  }
  $('[type=radio][name=serviceType]:first').attr('checked', 'checked');

  // Sự kiện bấm Enter trên textboxes
  $('.inputColumns').keypress(function(event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode === 13){
      NE.Conversion.addColumn(this);
    }
  });

  $('.btn-conversion').click(NE.Conversion.convert);
  $('.btn-add-column').click(function() {
    var target = $(this).closest('.card').find('.inputColumns');
    if(!target) return;

    NE.Conversion.addColumn(target);
  });

  // Sự kiện click các icons trên danh sách COLUMNS
  $(document).on('click', '.delete-column', function() {
    var li = $(this).closest('li');
    $(li).remove();
  })
  .on('click', '.up', function() {
    var li = $(this).closest('li');
    $(li).insertBefore($(li).prev());
  })
  .on('click', '.down', function() {
    var li = $(this).closest('li');
    $(li).insertAfter($(li).next());
  })
  ;
});

NE.Conversion = {
  addColumn: function(control) {
    var value = $(control).val();
    if(!value) {
      return;
    }

    var target = '#' + $(control).data('target');

    // Check duplicate
    var duplicated = false;
    $(target).find('.columnName').each(function(index, item) {
      if($(item).text().toLowerCase() === value.toLowerCase()) {
        duplicated = true;
        return false; // break
      }
    });

    if(duplicated) {
      NE.Dialog.error({ messages: 'Columns are duplicated' });
      return;
    }

    // If data is not duplicated, add new
    var template = $('#rowTemplate').html();
    template = $(template).find('.columnName').text(value).end();
    $(target).append($(template)[0].outerHTML);

    $(control).val('');
  },

  convert: function() {
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

    if(!$('#serviceId').val()) {
      NE.Dialog.error({ messages: 'Service ID can not be empty', ok: function() { $('#serviceId').focus(); } });
      return;
    }
    if(!$('#author').val()) {
      NE.Dialog.error({ messages: 'Author can not be empty', ok: function() { $('#author').focus(); } });
      return;
    }
    if(selectColumns.length === 0 && whereColumns.length === 0) {
      NE.Dialog.error({ messages: 'Columns can not be empty', ok: function() { $('#outParam').focus(); } });
      return;
    }

    // Lưu lại thông tin author để lần sau lấy ra hiển thị
    NE.Storage.set('author', $('#author').val());

    NE.sendRequest({
      data: JSON.stringify({
        selectColumns: selectColumns,
        whereColumns: whereColumns,
        serviceId: $('#serviceId').val(),
        author: $('#author').val(),
        serviceType: $('[type=radio][name=serviceType]:checked').val(),
      }),
      url: NE.urls.convert,
      success: function(downloadId) {
        // Download
        window.location.href = NE.urls.download + '?downloadId=' + downloadId;
      }
    });
  }
}