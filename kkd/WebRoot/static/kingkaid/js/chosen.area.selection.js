(function ($) {
    $.fn.areaSelection = function (in_opts) {
        // 组件字符串
        var _html_province = "<select data-bind='province'></select>";
        var _html_city = "<select data-bind='city'></select> ";
        var _html_district = "<select data-bind='district'></select>";
        var _html_option_default = "<option value='-1'>请选择</option>";

        // 选项
        var opts = {
            value: null,
            level: 3,
            // 用于和jquery.validator插件联动
            item_linkage: function(sel_value) {},
            area_cache: areacollection
        };

        $.extend(opts, in_opts || {});

        // 绘制组件
        this.append(_html_province);
        if(opts.level > 1) {
            this.append(" " + _html_city);
        }
        if(opts.level > 2) {
            this.append(" " + _html_district);
        }
        
        // 内部变量
        var _container = this;
        var _sel_province = this.find("[data-bind='province']");
        var _sel_city = this.find("[data-bind='city']");
        var _sel_district = this.find("[data-bind='district']");
        
        // 加载chosen插件
        this.find("select").chosen({ width: '87px', disable_search_threshold: 10 });

        // chosen插件的onchange事件处理
        _sel_province.chosen().change(function () {
            refresh_select(_sel_province.val(), find_next_level('province'));
            opts.item_linkage(get_com_value());
        });
        
        _sel_city.chosen().change(function () {
            refresh_select(_sel_city.val(), find_next_level('city'));
            opts.item_linkage(get_com_value());
        });
        
        if(_sel_district) {
            _sel_district.chosen().change(function () {
                refresh_select(_sel_district.val(), find_next_level('district'));
                opts.item_linkage(get_com_value());
            });     
        }
        
        // 初始化
    	initData();

        // 寻找子集
        function find_next_level(curr_level) {
            if(curr_level === 'province' && opts.level > 1) {
                return 'city';
            } else if(curr_level === 'city' && opts.level > 2) {
                return 'district';
            } else {
                return null;
            }
        }

        // 根据父级选中的节点的值刷新select
        function refresh_select(parent_data, level, init_value) {
            if(level) {
                var select = _container.find("[data-bind=" + level + "]");
                if(parent_data > -1) {
                	select.empty();
                	select.append(_html_option_default);
                    var data = getChoosenData(level, parent_data);
                    $.each(data, function (i, unit) {
                        select.append('<option value=' + unit.v + '>' + unit.t + '</option>');
                    });
                    if(init_value != null && init_value != '') {
                    	select.val(init_value);
                    }
                    select.trigger("chosen:updated");
                } else {
                    select.empty();
                    select.append(_html_option_default);
                    select.trigger("chosen:updated");
                }
                if(level == 'city' && _sel_district) {
                	 _sel_district.empty();
                	 _sel_district.append(_html_option_default);
                	 _sel_district.trigger("chosen:updated");
                }
            }
        }

        // 获取控件值，值是最小范围选择器的值
        function get_com_value() {
        	if(_sel_district.val() && _sel_district.val() != "-1") {
        		return _sel_district.val();
        	} else if (_sel_city.val() != "-1") {
        		return _sel_city.val();
        	} else {
        		return _sel_province.val();
        	}
        }
        
        // 获取控件数据
        function getChoosenData(level, parent_data) {
        	var provinces = opts.area_cache;
            if(level === 'city') {
            	for(var pi in provinces) {
            		if(provinces[pi].v === parent_data) {
            			return provinces[pi].c;
            		} 
            	}
            } else if(level === 'district') {
            	for(var pi in provinces) {
            		// 获取省值
            		var p_parent_data = parent_data.substring(0, 2) + '0000'; 
            		if(provinces[pi].v === p_parent_data) {
            			var cities = provinces[pi].c;
            			for(var ci in cities) {
            				if(cities[ci].v === parent_data) {
            					return cities[ci].c;
            				}
            			}
            		} 
            	}
            }
            return [];
        }
        
        // 初始化控件方法
        function initData() {
        	_sel_province.append(_html_option_default);
        	_sel_city.append(_html_option_default);
        	_sel_district.append(_html_option_default);
            $.each(opts.area_cache, function(i, province) {
                _sel_province.append('<option value=' + province.v + '>' + province.t + '</option>');
            });
            // 如果存在初始值，那么对控件进行初始化
            if(opts.value && opts.value.match(/^\d{6}$/) > 0) {
                var init_province = opts.value.substring(0, 2) + '0000'; 
                var init_city = opts.value.substring(0, 4) + '00';
                _sel_province.val(init_province);
                refresh_select(init_province, 'city', init_city);
                if(_sel_district) {
                	refresh_select(init_city, 'district', opts.value);
                }
            } else {
            	_sel_city.trigger("chosen:updated");
            	_sel_district.trigger("chosen:updated");
            }
            _sel_province.trigger("chosen:updated");
        }
    };
    
})(jQuery);