(function($) {
	$.fn.simplePagination = function(opts) {
		// 默认参数
		var dopts = {
			num_display_entries : 5,
			num_edge_entries : 2,
			items_per_page : 10,
			prev_text:'上一页',
			next_text:'下一页',
			show_if_single_page:true,
			callback : pageselectCallback,
			url : '',
			handle_data : function(data, page_no) {
				return true;
			},
			qcon_func : function() {
				return {};
			}
		};

		// 合并参数选项
		opts = $.extend({}, dopts, opts || {});

		// 总条数改变时的重建分页控件参数
		var ropts = {
			load_first_page : false,
			current_page : 0
		};
		ropts = $.extend({}, opts, ropts);

		function pageselectCallback(page_index, jq) {
			$.ajax({
				cache : false,
				type: "POST",
				dataType: "json",
				url : opts.url,
				data : (function() {
					return $.extend({
						pageNo : page_index,
						pageSize : opts.items_per_page
					}, opts.qcon_func() || {});
				})(),
				success : function(data) {
					if(opts.handle_data(data, page_index)) {
						var total = data.totalItem != null ? data.totalItem : data.body.totalItem; 
						ropts.current_page = page_index > Math.ceil(total
								/ opts.items_per_page) - 1 ? Math
								.ceil(total / opts.items_per_page) - 1
								: page_index;
						ropts.current_page = ropts.current_page < 0 ? 0 : ropts.current_page;
						jq.pagination(total, ropts);
					}
				}
			});
			return false;
		}

		this.pagination(0, opts);

	};
})(jQuery);
