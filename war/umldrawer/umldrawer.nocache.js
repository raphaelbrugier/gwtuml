function umldrawer(){
  var $intern_0 = '', $intern_28 = '" for "gwt:onLoadErrorFn"', $intern_26 = '" for "gwt:onPropertyErrorFn"', $intern_11 = '"><\/script>', $intern_13 = '#', $intern_63 = './dijit/themes/tundra/tundra.css', $intern_64 = './dojox/grid/resources/tundraGrid.css', $intern_55 = '.cache.html', $intern_15 = '/', $intern_49 = '293CE22D8BFE761CBEE77250E6324888', $intern_50 = '87587F94AA9C716CE02D9B9AD8F6571A', $intern_51 = '8BC8F152EA3C3958BFE53B1398F3DCDA', $intern_80 = '<script defer="defer">umldrawer.onScriptInjectionDone(\'umldrawer\')<\/script>', $intern_10 = '<script id="', $intern_68 = '<script language="javascript" src="', $intern_23 = '=', $intern_14 = '?', $intern_52 = 'B33AD5BDD13A2538734ACF0A1A5785A6', $intern_53 = 'B63635195D385E309A2038FE50EBF8AB', $intern_25 = 'Bad handler "', $intern_54 = 'C95838EB48289331B1FDCB5B92C2B3D4', $intern_66 = 'DOMContentLoaded', $intern_32 = 'GET', $intern_29 = 'MSXML2.XMLHTTP.3.0', $intern_30 = 'Microsoft.XMLHTTP', $intern_12 = 'SCRIPT', $intern_9 = '__gwt_marker_umldrawer', $intern_16 = 'base', $intern_4 = 'begin', $intern_3 = 'bootstrap', $intern_18 = 'clear.cache.gif', $intern_22 = 'content', $intern_67 = 'dojo/dojo.js', $intern_69 = 'dojo/dojo.js"><\/script>', $intern_8 = 'end', $intern_43 = 'gecko', $intern_44 = 'gecko1_8', $intern_65 = 'gwt-log.css', $intern_5 = 'gwt.hosted=', $intern_6 = 'gwt.hybrid', $intern_56 = 'gwt/chrome/chrome.css', $intern_27 = 'gwt:onLoadErrorFn', $intern_24 = 'gwt:onPropertyErrorFn', $intern_21 = 'gwt:property', $intern_61 = 'head', $intern_47 = 'hosted.html', $intern_60 = 'href', $intern_42 = 'ie6', $intern_41 = 'ie8', $intern_33 = 'iframe', $intern_17 = 'img', $intern_34 = 'javascript:""', $intern_57 = 'link', $intern_46 = 'loadExternalRefs', $intern_19 = 'meta', $intern_31 = 'moduleRequested', $intern_7 = 'moduleStartup', $intern_40 = 'msie', $intern_20 = 'name', $intern_37 = 'opera', $intern_35 = 'position:absolute; width:0; height:0; border:none', $intern_58 = 'rel', $intern_39 = 'safari', $intern_48 = 'selectingPermutation', $intern_2 = 'startup', $intern_59 = 'stylesheet', $intern_62 = 'tatami.css', $intern_1 = 'umldrawer', $intern_45 = 'unknown', $intern_36 = 'user.agent', $intern_38 = 'webkit', $intern_78 = 'yui/DDPlayer.js', $intern_79 = 'yui/DDPlayer.js"><\/script>', $intern_72 = 'yui/dom.js', $intern_73 = 'yui/dom.js"><\/script>', $intern_76 = 'yui/dragdrop.js', $intern_77 = 'yui/dragdrop.js"><\/script>', $intern_74 = 'yui/event.js', $intern_75 = 'yui/event.js"><\/script>', $intern_70 = 'yui/yahoo.js', $intern_71 = 'yui/yahoo.js"><\/script>';
  var $wnd = window, $doc = document, $stats = $wnd.__gwtStatsEvent?function(a){
    return $wnd.__gwtStatsEvent(a);
  }
  :null, injectedScriptsDone, gwtCodeEvaluated, bodyDone, scriptRequestCompleted, gwtFrameCreated, base = $intern_0, metaProps = {}, values = [], providers = [], answers = [], onLoadErrorFunc, propertyErrorFunc, scriptFrame, compiledScript = $intern_0;
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date()).getTime(), type:$intern_4});
  if (!$wnd.__gwt_stylesLoaded) {
    $wnd.__gwt_stylesLoaded = {};
  }
  if (!$wnd.__gwt_scriptsLoaded) {
    $wnd.__gwt_scriptsLoaded = {};
  }
  function isHostedMode(){
    var result = false;
    try {
      var query = $wnd.location.search;
      return (query.indexOf($intern_5) != -1 || $wnd.external && $wnd.external.gwtOnLoad) && query.indexOf($intern_6) == -1;
    }
     catch (e) {
    }
    isHostedMode = function(){
      return result;
    }
    ;
    return result;
  }

  var moduleStarted = false;
  function maybeStartModule(){
    if (bodyDone && (injectedScriptsDone && (gwtCodeEvaluated && (gwtFrameCreated && !moduleStarted)))) {
      moduleStarted = true;
      var frameWnd = scriptFrame.contentWindow;
      if (isHostedMode()) {
        frameWnd.__gwt_getProperty = function(name){
          return computePropValue(name);
        }
        ;
      }
      umldrawer = null;
      frameWnd.gwtOnLoad(onLoadErrorFunc, $intern_1, base);
      $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_7, millis:(new Date()).getTime(), type:$intern_8});
    }
  }

  function computeScriptBase(){
    var thisScript, markerId = $intern_9, markerScript;
    $doc.write($intern_10 + markerId + $intern_11);
    markerScript = $doc.getElementById(markerId);
    thisScript = markerScript && markerScript.previousSibling;
    while (thisScript && thisScript.tagName != $intern_12) {
      thisScript = thisScript.previousSibling;
    }
    function getDirectoryOfFile(path){
      var hashIndex = path.lastIndexOf($intern_13);
      if (hashIndex == -1) {
        hashIndex = path.length;
      }
      var queryIndex = path.indexOf($intern_14);
      if (queryIndex == -1) {
        queryIndex = path.length;
      }
      var slashIndex = path.lastIndexOf($intern_15, Math.min(queryIndex, hashIndex));
      return slashIndex >= 0?path.substring(0, slashIndex + 1):$intern_0;
    }

    ;
    if (thisScript && thisScript.src) {
      base = getDirectoryOfFile(thisScript.src);
    }
    if (base == $intern_0) {
      var baseElements = $doc.getElementsByTagName($intern_16);
      if (baseElements.length > 0) {
        base = baseElements[baseElements.length - 1].href;
      }
       else {
        base = getDirectoryOfFile($doc.location.href);
      }
    }
     else if (base.match(/^\w+:\/\//)) {
    }
     else {
      var img = $doc.createElement($intern_17);
      img.src = base + $intern_18;
      base = getDirectoryOfFile(img.src);
    }
    if (markerScript) {
      markerScript.parentNode.removeChild(markerScript);
    }
  }

  function processMetas(){
    var metas = document.getElementsByTagName($intern_19);
    for (var i = 0, n = metas.length; i < n; ++i) {
      var meta = metas[i], name = meta.getAttribute($intern_20), content;
      if (name) {
        if (name == $intern_21) {
          content = meta.getAttribute($intern_22);
          if (content) {
            var value, eq = content.indexOf($intern_23);
            if (eq >= 0) {
              name = content.substring(0, eq);
              value = content.substring(eq + 1);
            }
             else {
              name = content;
              value = $intern_0;
            }
            metaProps[name] = value;
          }
        }
         else if (name == $intern_24) {
          content = meta.getAttribute($intern_22);
          if (content) {
            try {
              propertyErrorFunc = eval(content);
            }
             catch (e) {
              alert($intern_25 + content + $intern_26);
            }
          }
        }
         else if (name == $intern_27) {
          content = meta.getAttribute($intern_22);
          if (content) {
            try {
              onLoadErrorFunc = eval(content);
            }
             catch (e) {
              alert($intern_25 + content + $intern_28);
            }
          }
        }
      }
    }
  }

  function unflattenKeylistIntoAnswers(propValArray, value){
    var answer = answers;
    for (var i = 0, n = propValArray.length - 1; i < n; ++i) {
      answer = answer[propValArray[i]] || (answer[propValArray[i]] = []);
    }
    answer[propValArray[n]] = value;
  }

  function computePropValue(propName){
    var value = providers[propName](), allowedValuesMap = values[propName];
    if (value in allowedValuesMap) {
      return value;
    }
    var allowedValuesList = [];
    for (var k in allowedValuesMap) {
      allowedValuesList[allowedValuesMap[k]] = k;
    }
    if (propertyErrorFunc) {
      propertyErrorFunc(propName, allowedValuesList, value);
    }
    throw null;
  }

  function newXhr(){
    if (window.XMLHttpRequest) {
      return new XMLHttpRequest();
    }
     else {
      try {
        return new ActiveXObject($intern_29);
      }
       catch (e) {
        return new ActiveXObject($intern_30);
      }
    }
  }

  function fetchCompiledScript(){
    $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_7, millis:(new Date()).getTime(), type:$intern_31});
    var xhr = newXhr();
    xhr.open($intern_32, base + initialHtml);
    xhr.onreadystatechange = function(){
      if (xhr.readyState == 4) {
        compiledScript = xhr.responseText;
        xhr = null;
        scriptRequestCompleted = true;
        maybeCreateGwtFrame();
      }
    }
    ;
    xhr.send(null);
  }

  function maybeCreateGwtFrame(){
    if (bodyDone && (scriptRequestCompleted && !gwtFrameCreated)) {
      scriptFrame = document.createElement($intern_33);
      scriptFrame.src = $intern_34;
      scriptFrame.id = $intern_1;
      scriptFrame.style.cssText = $intern_35;
      scriptFrame.tabIndex = -1;
      document.body.appendChild(scriptFrame);
      var win = scriptFrame.contentWindow;
      if (isHostedMode()) {
        win.name = $intern_1;
      }
      var doc = win.document;
      doc.open();
      doc.write(compiledScript);
      doc.close();
      gwtFrameCreated = true;
      maybeStartModule();
    }
  }

  providers[$intern_36] = function(){
    var ua = navigator.userAgent.toLowerCase();
    var makeVersion = function(result){
      return parseInt(result[1]) * 1000 + parseInt(result[2]);
    }
    ;
    if (ua.indexOf($intern_37) != -1) {
      return $intern_37;
    }
     else if (ua.indexOf($intern_38) != -1) {
      return $intern_39;
    }
     else if (ua.indexOf($intern_40) != -1) {
      if (document.documentMode >= 8) {
        return $intern_41;
      }
       else {
        var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
        if (result && result.length == 3) {
          var v = makeVersion(result);
          if (v >= 6000) {
            return $intern_42;
          }
        }
      }
    }
     else if (ua.indexOf($intern_43) != -1) {
      var result = /rv:([0-9]+)\.([0-9]+)/.exec(ua);
      if (result && result.length == 3) {
        if (makeVersion(result) >= 1008)
          return $intern_44;
      }
      return $intern_43;
    }
    return $intern_45;
  }
  ;
  values[$intern_36] = {gecko:0, gecko1_8:1, ie6:2, ie8:3, opera:4, safari:5};
  umldrawer.onScriptLoad = function(){
    gwtCodeEvaluated = true;
    maybeStartModule();
  }
  ;
  umldrawer.onScriptInjectionDone = function(){
    injectedScriptsDone = true;
    $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_46, millis:(new Date()).getTime(), type:$intern_8});
    maybeStartModule();
  }
  ;
  computeScriptBase();
  var strongName;
  var initialHtml;
  if (isHostedMode()) {
    if ($wnd.external && ($wnd.external.initModule && $wnd.external.initModule($intern_1))) {
      $wnd.location.reload();
      return;
    }
    initialHtml = $intern_47;
    strongName = $intern_0;
  }
  processMetas();
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date()).getTime(), type:$intern_48});
  if (!isHostedMode()) {
    try {
      unflattenKeylistIntoAnswers([$intern_41], $intern_49);
      unflattenKeylistIntoAnswers([$intern_42], $intern_50);
      unflattenKeylistIntoAnswers([$intern_37], $intern_51);
      unflattenKeylistIntoAnswers([$intern_44], $intern_52);
      unflattenKeylistIntoAnswers([$intern_43], $intern_53);
      unflattenKeylistIntoAnswers([$intern_39], $intern_54);
      strongName = answers[computePropValue($intern_36)];
      initialHtml = strongName + $intern_55;
    }
     catch (e) {
      return;
    }
  }
  fetchCompiledScript();
  var onBodyDoneTimerId;
  function onBodyDone(){
    if (!bodyDone) {
      bodyDone = true;
      maybeCreateGwtFrame();
      if (!__gwt_stylesLoaded[$intern_56]) {
        var l = $doc.createElement($intern_57);
        __gwt_stylesLoaded[$intern_56] = l;
        l.setAttribute($intern_58, $intern_59);
        l.setAttribute($intern_60, base + $intern_56);
        $doc.getElementsByTagName($intern_61)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_62]) {
        var l = $doc.createElement($intern_57);
        __gwt_stylesLoaded[$intern_62] = l;
        l.setAttribute($intern_58, $intern_59);
        l.setAttribute($intern_60, base + $intern_62);
        $doc.getElementsByTagName($intern_61)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_63]) {
        var l = $doc.createElement($intern_57);
        __gwt_stylesLoaded[$intern_63] = l;
        l.setAttribute($intern_58, $intern_59);
        l.setAttribute($intern_60, base + $intern_63);
        $doc.getElementsByTagName($intern_61)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_64]) {
        var l = $doc.createElement($intern_57);
        __gwt_stylesLoaded[$intern_64] = l;
        l.setAttribute($intern_58, $intern_59);
        l.setAttribute($intern_60, base + $intern_64);
        $doc.getElementsByTagName($intern_61)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_65]) {
        var l = $doc.createElement($intern_57);
        __gwt_stylesLoaded[$intern_65] = l;
        l.setAttribute($intern_58, $intern_59);
        l.setAttribute($intern_60, base + $intern_65);
        $doc.getElementsByTagName($intern_61)[0].appendChild(l);
      }
      maybeStartModule();
      if ($doc.removeEventListener) {
        $doc.removeEventListener($intern_66, onBodyDone, false);
      }
      if (onBodyDoneTimerId) {
        clearInterval(onBodyDoneTimerId);
      }
    }
  }

  if ($doc.addEventListener) {
    $doc.addEventListener($intern_66, function(){
      onBodyDone();
    }
    , false);
  }
  var onBodyDoneTimerId = setInterval(function(){
    if (/loaded|complete/.test($doc.readyState)) {
      onBodyDone();
    }
  }
  , 50);
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date()).getTime(), type:$intern_8});
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_46, millis:(new Date()).getTime(), type:$intern_4});
  if (!__gwt_scriptsLoaded[$intern_67]) {
    __gwt_scriptsLoaded[$intern_67] = true;
    document.write($intern_68 + base + $intern_69);
  }
  if (!__gwt_scriptsLoaded[$intern_70]) {
    __gwt_scriptsLoaded[$intern_70] = true;
    document.write($intern_68 + base + $intern_71);
  }
  if (!__gwt_scriptsLoaded[$intern_72]) {
    __gwt_scriptsLoaded[$intern_72] = true;
    document.write($intern_68 + base + $intern_73);
  }
  if (!__gwt_scriptsLoaded[$intern_74]) {
    __gwt_scriptsLoaded[$intern_74] = true;
    document.write($intern_68 + base + $intern_75);
  }
  if (!__gwt_scriptsLoaded[$intern_76]) {
    __gwt_scriptsLoaded[$intern_76] = true;
    document.write($intern_68 + base + $intern_77);
  }
  if (!__gwt_scriptsLoaded[$intern_78]) {
    __gwt_scriptsLoaded[$intern_78] = true;
    document.write($intern_68 + base + $intern_79);
  }
  $doc.write($intern_80);
}

umldrawer();
