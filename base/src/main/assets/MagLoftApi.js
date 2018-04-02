(function() {
  const state = { requestId: 0, handlers: [] };

  window.MagLoftApi = function(action, data={}) {
    const requestId = window.MagLoftApi.nextRequestId();
    return new Promise(function(resolve, reject) {
      state.handlers[requestId] = { resolve, reject };
      MagLoftApiBridge.request(JSON.stringify({ action, data, requestId }));
    })
  };

  window.MagLoftApi.nextRequestId = function() {
    const requestId = state.requestId;
    state.requestId = state.requestId + 1;
    return requestId;
  };

  window.MagLoftApi.resolve = function(requestId, data) {
    if (state.handlers[requestId]) {
      state.handlers[requestId].resolve(data);
    }
  };
  
  window.MagLoftApi.reject = function(requestId, data) {
    if (state.handlers[requestId]) {
      state.handlers[requestId].reject(data);
    }
  };
})();
