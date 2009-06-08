function umldrawer(){
  var $intern_0 = '', $intern_28 = '" for "gwt:onLoadErrorFn"', $intern_26 = '" for "gwt:onPropertyErrorFn"', $intern_11 = '"><\/script>', $intern_13 = '#', $intern_65 = './dijit/themes/tundra/tundra.css', $intern_66 = './dojox/grid/resources/tundraGrid.css', $intern_57 = '.cache.html', $intern_15 = '/', $intern_51 = '3D1DD37A5F673975864C6E7069B3D2DB', $intern_52 = '43EA976D992AB5C63C2E5F94939ABB37', $intern_53 = '5A829F09BA370227F7F830C1E23DD840', $intern_32 = "<div id='umldrawer.container' style='position:absolute; width:0; height:0; border:none'><\/div>", $intern_82 = '<script defer="defer">umldrawer.onInjectionDone(\'umldrawer\')<\/script>', $intern_10 = '<script id="', $intern_70 = '<script language="javascript" src="', $intern_23 = '=', $intern_14 = '?', $intern_25 = 'Bad handler "', $intern_54 = 'C0572BAFBC81F51977867086A52137B7', $intern_55 = 'CA5B8030262007A1CAE2F1F705F191EB', $intern_68 = 'DOMContentLoaded', $intern_56 = 'F68A81DC091C29708BF690F8A3987BA9', $intern_33 = 'GET', $intern_29 = 'MSXML2.XMLHTTP.3.0', $intern_30 = 'Microsoft.XMLHTTP', $intern_12 = 'SCRIPT', $intern_9 = '__gwt_marker_umldrawer', $intern_16 = 'base', $intern_4 = 'begin', $intern_3 = 'bootstrap', $intern_18 = 'clear.cache.gif', $intern_22 = 'content', $intern_69 = 'dojo/dojo.js', $intern_71 = 'dojo/dojo.js"><\/script>', $intern_8 = 'end', $intern_45 = 'gecko', $intern_46 = 'gecko1_8', $intern_67 = 'gwt-log.css', $intern_5 = 'gwt.hosted=', $intern_6 = 'gwt.hybrid', $intern_58 = 'gwt/chrome/chrome.css', $intern_27 = 'gwt:onLoadErrorFn', $intern_24 = 'gwt:onPropertyErrorFn', $intern_21 = 'gwt:property', $intern_63 = 'head', $intern_49 = 'hosted.html', $intern_62 = 'href', $intern_44 = 'ie6', $intern_43 = 'ie8', $intern_34 = 'iframe', $intern_17 = 'img', $intern_35 = 'javascript:""', $intern_59 = 'link', $intern_48 = 'loadExternalRefs', $intern_19 = 'meta', $intern_31 = 'moduleRequested', $intern_7 = 'moduleStartup', $intern_42 = 'msie', $intern_20 = 'name', $intern_39 = 'opera', $intern_36 = 'position:absolute; width:0; height:0; border:none', $intern_60 = 'rel', $intern_41 = 'safari', $intern_50 = 'selectingPermutation', $intern_2 = 'startup', $intern_61 = 'stylesheet', $intern_64 = 'tatami.css', $intern_1 = 'umldrawer', $intern_37 = 'umldrawer.container', $intern_47 = 'unknown', $intern_38 = 'user.agent', $intern_40 = 'webkit', $intern_80 = 'yui/DDPlayer.js', $intern_81 = 'yui/DDPlayer.js"><\/script>', $intern_74 = 'yui/dom.js', $intern_75 = 'yui/dom.js"><\/script>', $intern_78 = 'yui/dragdrop.js', $intern_79 = 'yui/dragdrop.js"><\/script>', $intern_76 = 'yui/event.js', $intern_77 = 'yui/event.js"><\/script>', $intern_72 = 'yui/yahoo.js', $intern_73 = 'yui/yahoo.js"><\/script>';
  var $wnd = window, $doc = document, $stats = $wnd.__gwtStatsEvent?function(a){
    return $wnd.__gwtStatsEvent(a);
  }
  :null, scriptsDone, loadDone, bodyDone, base = $intern_0, metaProps = {}, values = [], providers = [], answers = [], onLoadErrorFunc, propertyErrorFunc, scriptFrame;
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
    if (bodyDone && (scriptsDone && (loadDone && !moduleStarted))) {
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
    document.write($intern_32);
    var xhr = newXhr();
    xhr.open($intern_33, base + initialHtml);
    xhr.onreadystatechange = function(){
      if (xhr.readyState == 4) {
        scriptFrame = document.createElement($intern_34);
        scriptFrame.src = $intern_35;
        scriptFrame.id = $intern_1;
        scriptFrame.style.cssText = $intern_36;
        scriptFrame.tabIndex = -1;
        document.getElementById($intern_37).appendChild(scriptFrame);
        var win = scriptFrame.contentWindow;
        if (isHostedMode()) {
          win.name = $intern_1;
        }
        var doc = win.document;
        doc.open();
        doc.write(xhr.responseText);
        doc.close();
        xhr = null;
      }
    }
    ;
    xhr.send(null);
  }

  providers[$intern_38] = function(){
    var ua = navigator.userAgent.toLowerCase();
    var makeVersion = function(result){
      return parseInt(result[1]) * 1000 + parseInt(result[2]);
    }
    ;
    if (ua.indexOf($intern_39) != -1) {
      return $intern_39;
    }
     else if (ua.indexOf($intern_40) != -1) {
      return $intern_41;
    }
     else if (ua.indexOf($intern_42) != -1) {
      if (document.documentMode >= 8) {
        return $intern_43;
      }
       else {
        var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
        if (result && result.length == 3) {
          var v = makeVersion(result);
          if (v >= 6000) {
            return $intern_44;
          }
        }
      }
    }
     else if (ua.indexOf($intern_45) != -1) {
      var result = /rv:([0-9]+)\.([0-9]+)/.exec(ua);
      if (result && result.length == 3) {
        if (makeVersion(result) >= 1008)
          return $intern_46;
      }
      return $intern_45;
    }
    return $intern_47;
  }
  ;
  values[$intern_38] = {gecko:0, gecko1_8:1, ie6:2, ie8:3, opera:4, safari:5};
  umldrawer.onScriptLoad = function(){
    loadDone = true;
    maybeStartModule();
  }
  ;
  umldrawer.onInjectionDone = function(){
    scriptsDone = true;
    $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_48, millis:(new Date()).getTime(), type:$intern_8});
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
    initialHtml = $intern_49;
    strongName = $intern_0;
  }
  processMetas();
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date()).getTime(), type:$intern_50});
  if (!isHostedMode()) {
    try {
      unflattenKeylistIntoAnswers([$intern_43], $intern_51);
      unflattenKeylistIntoAnswers([$intern_41], $intern_52);
      unflattenKeylistIntoAnswers([$intern_46], $intern_53);
      unflattenKeylistIntoAnswers([$intern_44], $intern_54);
      unflattenKeylistIntoAnswers([$intern_39], $intern_55);
      unflattenKeylistIntoAnswers([$intern_45], $intern_56);
      strongName = answers[computePropValue($intern_38)];
      initialHtml = strongName + $intern_57;
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
      if (!__gwt_stylesLoaded[$intern_58]) {
        var l = $doc.createElement($intern_59);
        __gwt_stylesLoaded[$intern_58] = l;
        l.setAttribute($intern_60, $intern_61);
        l.setAttribute($intern_62, base + $intern_58);
        $doc.getElementsByTagName($intern_63)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_64]) {
        var l = $doc.createElement($intern_59);
        __gwt_stylesLoaded[$intern_64] = l;
        l.setAttribute($intern_60, $intern_61);
        l.setAttribute($intern_62, base + $intern_64);
        $doc.getElementsByTagName($intern_63)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_65]) {
        var l = $doc.createElement($intern_59);
        __gwt_stylesLoaded[$intern_65] = l;
        l.setAttribute($intern_60, $intern_61);
        l.setAttribute($intern_62, base + $intern_65);
        $doc.getElementsByTagName($intern_63)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_66]) {
        var l = $doc.createElement($intern_59);
        __gwt_stylesLoaded[$intern_66] = l;
        l.setAttribute($intern_60, $intern_61);
        l.setAttribute($intern_62, base + $intern_66);
        $doc.getElementsByTagName($intern_63)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_67]) {
        var l = $doc.createElement($intern_59);
        __gwt_stylesLoaded[$intern_67] = l;
        l.setAttribute($intern_60, $intern_61);
        l.setAttribute($intern_62, base + $intern_67);
        $doc.getElementsByTagName($intern_63)[0].appendChild(l);
      }
      maybeStartModule();
      if ($doc.removeEventListener) {
        $doc.removeEventListener($intern_68, onBodyDone, false);
      }
      if (onBodyDoneTimerId) {
        clearInterval(onBodyDoneTimerId);
      }
    }
  }

  if ($doc.addEventListener) {
    $doc.addEventListener($intern_68, function(){
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
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_48, millis:(new Date()).getTime(), type:$intern_4});
  if (!__gwt_scriptsLoaded[$intern_69]) {
    __gwt_scriptsLoaded[$intern_69] = true;
    document.write($intern_70 + base + $intern_71);
  }
  if (!__gwt_scriptsLoaded[$intern_72]) {
    __gwt_scriptsLoaded[$intern_72] = true;
    document.write($intern_70 + base + $intern_73);
  }
  if (!__gwt_scriptsLoaded[$intern_74]) {
    __gwt_scriptsLoaded[$intern_74] = true;
    document.write($intern_70 + base + $intern_75);
  }
  if (!__gwt_scriptsLoaded[$intern_76]) {
    __gwt_scriptsLoaded[$intern_76] = true;
    document.write($intern_70 + base + $intern_77);
  }
  if (!__gwt_scriptsLoaded[$intern_78]) {
    __gwt_scriptsLoaded[$intern_78] = true;
    document.write($intern_70 + base + $intern_79);
  }
  if (!__gwt_scriptsLoaded[$intern_80]) {
    __gwt_scriptsLoaded[$intern_80] = true;
    document.write($intern_70 + base + $intern_81);
  }
  $doc.write($intern_82);
}

umldrawer();
