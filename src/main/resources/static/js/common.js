"use strict";
var NEprocessing = false;
if (typeof NProgress !== 'undefined') {
  window.onload = function () {
    controlProcessingStatus();
  }
}

/**
 * Fix NProgress for IE
 * (on IE, document.ready run after window.onload)
 * (on Chrome, document.ready run before window.onload)
 */
function controlProcessingStatus() {
  if (NEprocessing) {
    NProgress.done();
    NEprocessing = false;
  } else {
    NProgress.start();
    NEprocessing = true;
  }
}

$(function () {
  if (typeof NProgress !== 'undefined') {
    controlProcessingStatus();
  }
  // $('#page-content-wrapper').mCustomScrollbar({setHeight:(window.innerHeight-$('header').outerHeight())})

  $('[data-toggle="tooltip"]').tooltip({container: '#page-content-wrapper'});

  /**
   * Add csrf token for ajax requests (POST, PUT, DELETE, ...)
   */
  $(document).ajaxSend(function (e, xhr, options) {
    if (!options || options.type === 'GET') {
      return;
    }

    var csrfToken = $("meta[name='_csrf']").attr('content');
    xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
  }).ajaxStart(function () {
    $('#ne-loader').show();
    NProgress.start();
  }).ajaxStop(function () {
    $('#ne-loader').hide();
    NProgress.done();
  });

  // $.fn.dataTable && $.extend(true, $.fn.dataTable.defaults, {
  //     column: {
  //         render: $.fn.dataTable.render.text()
  //     }
  // });

  /**
   * Logout
   */
  $('#logout').on('click', function (e) {
    e.preventDefault();
    NE.submit(NE.urls.logout)
  });

})

var NE = NE || {};
$.extend(true, NE, {
  urls: {},
  messages: {},
  labels: {},
  settings: {},

  /**
   * Send request to server and get JSON response
   * @param options
   */
  sendRequest: function (options) {
    var settings = $.extend(
        {
          type: 'POST',
        },
        this._ajaxCommonOptions(options),
        options,
        {
          success: function (retData) {
            if (retData.success) {
              options.success && options.success(retData.response);
            } else {
              if (options.error) {
                options.error(retData.messages);
              } else {
                NE.Dialog.error({
                  messages: retData.messages
                });
              }
            }
          }//success
        }
    );
    $.ajax(settings);
  },

  /**
   * Send request to server and get html response
   * @param options
   */
  html: function (options) {
    var settings = $.extend(
        {
          type: 'GET',
        },
        this._ajaxCommonOptions(options),
        options,
        {
          success: function (retData) {
            options.success(retData);
          },
        }
    );
    $.ajax(settings);
  },

  /**
   * Submit a form to server by ajax
   * @param url Url to submit form
   * @param data Form data
   */
  submit: function (url, data) {
    if (data == null) {
      data = {};
    }

    var form = $('<form>').attr({
      method: 'POST',
      action: url
    }).css({
      display: 'none'
    });

    // Add form data
    for (var key in data) {
      if (data.hasOwnProperty(key)) {
        form.append(this._addData(key, data[key]));
      }
    }
    // Add CSRF token
    var csrfToken = $("meta[name='_csrf']").attr('content');
    form.append(this._addData('_csrf', csrfToken));

    $(document.body).append(form);
    form.submit();
  },

  /**
   * Create form data
   *
   * @param name Name of field
   * @param data Value of field
   * @returns {*} jQuery element of input data
   * @private
   */
  _addData: function (name, data) {
    if ($.isArray(data)) {
      for (var i = 0; i < data.length; i++) {
        var value = data[i];
        return this._addData(name + '[' + i + ']', value);
      }
    } else if (typeof data === 'object') {
      for (var key in data) {
        if (data.hasOwnProperty(key)) {
          return this._addData(name + '[' + key + ']', data[key]);
        }
      }
    } else if (data != null) {
      return $('<input>').attr({
        type: 'hidden',
        name: String(name),
        value: String(data)
      });
    }
  },

  /**
   * Default handler options for AJAX request (in case of error and completed request)
   */
  _ajaxCommonOptions: function (options) {
    return {
      contentType: 'application/json; charset=utf-8',
      async: true,
      cache: false,
      processData: false,
      error: function (jqXHR, textStatus, errorThrown) {
        if (options.error) {
          options.error(jqXHR, textStatus, errorThrown);
        } else {
          NE.Dialog.error({ messages: 'System error occurs' });
          console.log(jqXHR, textStatus, errorThrown);
        }
      },
      complete: function (retData) {
        if (options.complete) {
          options.complete(retData);
        }
      }
    };
  },

  /**
   * Update moment date (a minute ago, 2 hours ago, ...)
   * Target: DOM have `.NE-moment-date` class and timestamp stored in attribute `data-timestamp`
   * @param container Update in this container. If container is null or underfined, update in all page
   */
  updateMomentDate: function (container) {
    var target = container ? $(container + ' .NE-moment-date') : $('.NE-moment-date');
    target.each(function (index, item) {
      var originalDate = $(item).data('timestamp');
      $(item).html(moment(originalDate).fromNow());
    });
  }
});

/**
 * Dialog utility
 */
NE.Dialog = {
  /**
   * Show dialog
   * settings: dialog settings
   *    - messages: Array of messages
   *    - title: title of dialog. Default is `dialogType`
   *    - dialogType: type of dialog ('error', 'warning', 'info', 'confirm')
   *    - button: 0-OK/Cancel, 1-OK, 2-Cancel. Default is 0-OK/Cancel
   *    - defaultButton: Default focused button.
   *             0: OK, 1: Cancel.
   *             Default is 0: OK
   *    - buttonLabels: { ok: 'OK', cancel: 'Cancel'}
   *    - ok: callback function when user click OK button
   *    - cancel: callback function when user click Cancel button
   */
  show: function (settings) {
    var type = 0;
    if (settings.dialogType === DialogType.Error) {
      type = 1;
    }
    // Default setting
    settings = $.extend({
      dialogType: DialogType.Info,
      button: type,
      defaultButton: 0,
      buttonLabels: {
        ok: 'OK',
        cancel: 'Cancel'
      }
    }, settings);
    settings.title = settings.title || settings.dialogType.toUpperCase();
    var dialogContainer = $("<div/>").attr({
      id: 'ne-dialog-' + new Date().getTime(),
      'class': 'modal fade ne-modal-dialog',
      'role': 'dialog'
    });

    var NEDialog = this._createDialog(dialogContainer, settings);
    $('body').append(NEDialog);
    NEDialog.on('hide.bs.modal', function () {
      // Get button clicked
      var button = $(dialogContainer).find('input[name=dialog-result]').val();

      // And call callback functions
      switch (button) {
        case ButtonType.OK:
          settings.ok && settings.ok();
          break;
        case ButtonType.Cancel:
          settings.cancel && settings.cancel();
          break;
      }
      $(NEDialog).remove();
    }).modal({
      backdrop: 'static',
      keyboard: false
    });
  },

  error: function (settings) {
    this._showWithType(settings, DialogType.Error);
  },

  warning: function (settings) {
    this._showWithType(settings, DialogType.Warning);
  },

  info: function (settings) {
    this._showWithType(settings, DialogType.Info);
  },

  confirm: function (settings) {
    this._showWithType(settings, DialogType.Confirm);
  },

  /**
   *
   * @param settings string|object Message or setting
   * @param dialogType Diaglog type
   * @private
   */
  _showWithType: function (settings, dialogType) {
    settings.dialogType = dialogType;
    this.show(settings);
  },

  /**
   * Hide a dialog
   */
  hide: function () {
    $('div.ne-modal-dialog.modal[role=dialog]').empty();
    $('div.modal-backdrop').remove();
  },

  /**
   * Create an instance of dialog
   */
  _createDialog: function (dialogContainer, settings) {
    var dialogIcon = '';
    var textColor = '';
    switch (settings.dialogType) {
      case DialogType.Error:
        dialogIcon = 'minus-circle';
        textColor = 'text-danger';
        break;

      case DialogType.Warning:
        dialogIcon = 'exclamation-circle';
        textColor = 'text-warning';
        break;

      case DialogType.Info:
        dialogIcon = 'info-circle';
        textColor = 'text-info';
        break;

      case DialogType.Confirm:
        dialogIcon = 'question-circle';
        textColor = 'text-primary';
        break;
    }
    dialogIcon = "ne-dialog-icon fas fa-" + dialogIcon + ' ' + textColor;

    // Dialog header
    var header = $('<div class="modal-header d-flex justify-content-center" />').append(
        $('<strong>', {'class': 'modal-title ' + textColor}).text(settings.title));

    // Dialog body
    var body = $('<div class="modal-body" />')
    .append($('<div class="d-flex align-items-center"/>')
        .append($('<div/>').attr({'class': dialogIcon}))
        .append($('<div class="ne-dialog-message"/>')
        .append(this._makeContent(settings.messages)))
    );
    // Dialog footer
    var footer = $('<div class="modal-footer" />')
    .append($('<input/>').attr({
      'type': 'hidden',
      'name': 'dialog-result'
    }));
    if (settings.button === 0 || settings.button === 1)	// OK
    {
      footer.append(
          $('<button type="button" name="ok" class="btn btn-primary btn-sm" data-dismiss="modal" />')
          .text(settings.buttonLabels.ok)
          .on('click', function () {
            $(dialogContainer).find('input[name=dialog-result]').val(
                ButtonType.OK);
          }));
    }
    if (settings.button === 0 || settings.button === 2)	// Cancel
    {
      footer.append(
          $('<button type="button" name="cancel" class="btn btn-secondary btn-sm" data-dismiss="modal" />')
          .text(settings.buttonLabels.cancel)
          .on('click', function () {
            $(dialogContainer).find('input[name=dialog-result]').val(
                ButtonType.Cancel);
          }));
    }

    // Construct dialog message
    var dialog = dialogContainer
    .append(
        $('<div class="modal-dialog modal-dialog-centered" role="document" />')
        .append($('<div class="modal-content" />')
            .append(header)
            .append(body)
            .append(footer)
        )
    );

    return dialog;
  },
  _makeContent: function (messages) {
    if (!Array.isArray(messages)) {
      messages = [messages]; // cast string to array
    }

    if (messages.length > 0 && '0' in Object(messages)) {
      var content = $('<ul>')
      $.each(messages, function (index, item) {
        if (typeof item === 'string') {
          content.append($('<li/>').append(item));
        } else {
          var msg = $('<li/>');
          item.messageCd && msg.append($('<strong/>', {text: item.messageCd + ': '}));
          msg.append(item.messageContent);
          content.append(msg);
        }
      });
      return content;
    }
    return messages.message;
  }
};
var DialogType = {
  Error: 'Error',
  Warning: 'Warning',
  Info: 'Information',
  Confirm: 'Confirm'
};
var ButtonType = {
  OK: '1',
  Cancel: '2'
};

/**
 * Working with local/session storage
 *
 * @type {{}}
 */
NE.Storage = {
  set: function (key, value) {
    this.getInstance().setItem(key, value);
  },
  get: function (key) {
    return this.getInstance().getItem(key);
  },
  clear: function (key) {
    if (key) {
      this.getInstance().removeItem(key);
    } else {
      this.getInstance().clear();
    }
  },
  getInstance: function () {
    return window.localStorage;
  }
};

if (!String.prototype.format) {
  String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
      return typeof args[number] != 'undefined'
        ? args[number]
        : match
      ;
    });
  };
}